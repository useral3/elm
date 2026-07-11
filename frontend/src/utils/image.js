/**
 * 智能图片路径处理
 * - /uploads/xxx → 后端文件，保留原路径
 * - http(s)://xxx → 绝对URL，保留原样
 * - 其他/空 → 从前端 public/img/ 提供
 */
export function imgUrl(dbPath, fallback = 'img1.png') {
  if (!dbPath) return '/img/' + fallback
  if (dbPath.startsWith('http')) return dbPath
  if (dbPath.startsWith('/uploads/')) return dbPath    // 后端上传文件
  if (dbPath.startsWith('/img/')) return dbPath          // 已经是前端图片路径
  const filename = dbPath.replace(/^.*[/\\]/, '')
  return '/img/' + filename
}
