/**
 * 判断设备的类型
 */
export default function() {
  const agent = navigator.userAgent
  const isWindowsPhone = /Windows Phone/.test(agent)
  const isSymbian = /SymbianOS/.test(agent) || isWindowsPhone
  const isAndroid = /Android/.test(agent)
  const isFireFox = /Firefox/.test(agent)
  // const isChrome = /Chrome|CriOS/.test(agent)
  const isTablet = /iPad|PlayBook/.test(agent) || (isAndroid && !/Mobile/.test(agent)) || (isFireFox && /Tablet/.test(agent))
  const isIPhone = /iPhone/.test(agent) && !isTablet
  const isDesktop = !isIPhone && !isAndroid && !isSymbian
  return {
    isDesktop: isDesktop,
    isTablet: isTablet,
    isMobile: !isDesktop && !isTablet,
    isIPhone: isIPhone,
    isAndroid: isAndroid,
    type: isDesktop ? 'desktop' : isTablet ? 'tablet' : 'mobile'
  }
}
