package org.example.elm.service.impl;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.example.elm.entity.ChatRequest;
import org.example.elm.entity.ChatResponse;
import org.example.elm.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ChatServiceImpl implements ChatService {

    @Value("${dashscope.api.key:}")
    private String apiKey;

    private static final String DASHSCOPE_API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    private OkHttpClient httpClient;

    @PostConstruct
    public void init() {
        // 创建重试拦截器（最多重试3次）
        RetryInterceptor retryInterceptor = new RetryInterceptor(3, 1000);

        httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(retryInterceptor)  // 添加重试拦截器
                .build();
    }

    @Override
    public ChatResponse chat(ChatRequest request) {
        // 如果没有配置API密钥，则使用模拟响应
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return mockResponse(request);
        }

        try {
            // 构建请求体
            String requestBodyJson = buildDashScopeRequestBody(request.getMessage());

            RequestBody body = RequestBody.create(
                    MediaType.get("application/json; charset=utf-8"),
                    requestBodyJson
            );

            Request httpRequest = new Request.Builder()
                    .url(DASHSCOPE_API_URL)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            Response response = httpClient.newCall(httpRequest).execute();
            String responseBody = response.body().string();

            if (response.isSuccessful()) {
                // 解析API响应
                String reply = parseApiResponse(responseBody);
                return new ChatResponse(reply, true, null, request.getSessionId());
            } else {
                // 提供更详细的错误信息
                String errorDetails = "API请求失败: " + response.code() + " " + response.message() + "\n" +
                                     "响应体: " + responseBody;
                System.err.println("API调用错误详情: " + errorDetails);
                return new ChatResponse(null, false, errorDetails, request.getSessionId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatResponse(null, false, "请求过程中发生错误: " + e.getMessage(), request.getSessionId());
        }
    }

    private String buildDashScopeRequestBody(String prompt) {
        return "{\n" +
                "  \"model\": \"qwen-max\",\n" +
                "  \"input\": {\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"role\": \"system\",\n" +
                "        \"content\": \"你是一个智能客服，专门回答关于外卖平台的问题。请友好、专业地回答用户的问题。\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"role\": \"user\",\n" +
                "        \"content\": \"" + prompt + "\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"parameters\": {}\n" +
                "}";
    }

    private String parseApiResponse(String responseBody) {
        try {
            // 使用fastjson解析响应
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(responseBody);
            
            // 检查是否有错误信息
            if (jsonObject.containsKey("error")) {
                String errorMessage = jsonObject.getString("error");
                System.err.println("API返回错误: " + errorMessage);
                return "API返回错误: " + errorMessage;
            }
            
            // 尝试多种可能的响应格式
            if (jsonObject.containsKey("output") && jsonObject.getJSONObject("output").containsKey("choices")) {
                com.alibaba.fastjson.JSONArray choices = jsonObject.getJSONObject("output").getJSONArray("choices");
                if (choices != null && !choices.isEmpty()) {
                    com.alibaba.fastjson.JSONObject firstChoice = choices.getJSONObject(0);
                    if (firstChoice.containsKey("message")) {
                        return firstChoice.getString("message");
                    } else if (firstChoice.containsKey("text")) {
                        return firstChoice.getString("text");
                    }
                }
            }
            
            // 如果以上都不匹配，尝试直接返回整个响应体中的内容
            if (jsonObject.containsKey("output") && jsonObject.getJSONObject("output").containsKey("text")) {
                return jsonObject.getJSONObject("output").getString("text");
            }
            
            // 如果还是找不到，尝试从其他字段获取内容
            if (jsonObject.containsKey("response")) {
                return jsonObject.getString("response");
            }
            
            // 如果还是找不到，返回原始响应体的摘要
            return "无法解析回复内容。原始响应: " + responseBody.substring(0, Math.min(200, responseBody.length()));
        } catch (Exception e) {
            e.printStackTrace();
            return "抱歉，解析回复时出现错误: " + e.getMessage();
        }
    }

    /**
     * 自定义重试拦截器：实现自动重试机制
     * - 最多重试 3 次
     * - 指数退避策略：1s → 2s → 4s
     * - 只重试可恢复的错误：超时、网络异常、5xx 服务端错误
     */
    static class RetryInterceptor implements Interceptor {
        private final int maxRetries;
        private final long initialBackoffMs;

        public RetryInterceptor(int maxRetries, long initialBackoffMs) {
            this.maxRetries = maxRetries;
            this.initialBackoffMs = initialBackoffMs;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            IOException lastException = null;

            for (int attempt = 0; attempt <= maxRetries; attempt++) {
                try {
                    Response response = chain.proceed(request);
                    
                    // 如果是服务端错误（5xx），可以重试
                    if (response.code() >= 500 && attempt < maxRetries) {
                        response.close();  // 关闭响应体，避免资源泄漏
                        
                        // 指数退避等待
                        long backoffMs = initialBackoffMs * (long) Math.pow(2, attempt);
                        System.out.println("[重试] API 返回 " + response.code() + "，第 " + (attempt + 1) + " 次重试，等待 " + backoffMs + "ms");
                        
                        try {
                            Thread.sleep(backoffMs);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new IOException("重试被中断", ie);
                        }
                        continue;
                    }
                    
                    return response;
                    
                } catch (SocketTimeoutException e) {
                    lastException = e;
                    
                    if (attempt < maxRetries) {
                        long backoffMs = initialBackoffMs * (long) Math.pow(2, attempt);
                        System.out.println("[重试] 请求超时，第 " + (attempt + 1) + " 次重试，等待 " + backoffMs + "ms");
                        
                        try {
                            Thread.sleep(backoffMs);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new IOException("重试被中断", ie);
                        }
                    }
                } catch (IOException e) {
                    // 网络异常也可以重试
                    lastException = e;
                    
                    if (attempt < maxRetries) {
                        long backoffMs = initialBackoffMs * (long) Math.pow(2, attempt);
                        System.out.println("[重试] 网络异常，第 " + (attempt + 1) + " 次重试，等待 " + backoffMs + "ms");
                        
                        try {
                            Thread.sleep(backoffMs);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new IOException("重试被中断", ie);
                        }
                    }
                }
            }

            throw new IOException("重试 " + maxRetries + " 次后仍然失败", lastException);
        }
    }

    // 模拟响应，当没有配置API密钥时使用
    private ChatResponse mockResponse(ChatRequest request) {
        String sessionId = request.getSessionId();
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }

        String message = request.getMessage().toLowerCase();
        String reply;

        if (message.contains("你好") || message.contains("您好") || message.contains("hello")) {
            reply = "您好！欢迎使用我们的智能客服。我可以帮助您解答关于订单、配送、支付等方面的问题。";
        } else if (message.contains("订单") || message.contains("order")) {
            reply = "您可以查看订单状态、取消订单或联系配送员。请问您需要哪方面的帮助？";
        } else if (message.contains("配送") || message.contains("delivery") || message.contains("快递") || message.contains("物流")) {
            reply = "配送相关的问题包括：查看配送进度、修改配送地址、联系配送员等。请告诉我您的具体需求。";
        } else if (message.contains("支付") || message.contains("付款") || message.contains("pay")) {
            reply = "我们支持多种支付方式，包括微信支付、支付宝和银行卡支付。遇到支付问题请联系客服。";
        } else if (message.contains("退款") || message.contains("refund")) {
            reply = "退款通常在3-5个工作日内到账。如果您申请了退款但未收到，请提供订单号以便查询。";
        } else if (message.contains("帮助") || message.contains("help") || message.contains("客服")) {
            reply = "我是您的智能客服助手。我可以帮助您解答常见问题。如果问题复杂，我会为您转接人工客服。";
        } else {
            reply = "感谢您的咨询！这是一个智能客服演示。在实际部署中，这里会接入通义千问API来提供更准确的回答。";
        }

        return new ChatResponse(reply, true, null, sessionId);
    }
}