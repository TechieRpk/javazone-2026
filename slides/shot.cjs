const { chromium } = require('playwright');
(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage({ viewport: { width: 1280, height: 720 } });
  for (let i = 1; i <= 20; i++) {
    await page.goto(`http://localhost:3131/${i}`, { waitUntil: 'networkidle' });
    const text = await page.textContent('body');
    if (text.includes('What hand-written clients cost you')) {
      await page.waitForTimeout(500);
      await page.screenshot({ path: '/tmp/slide-costs.png' });
      console.log('found at', i);
    }
    if (text.includes('Drake') || text.includes('Reading the API')) {
      console.log('drake-ish content at', i);
    }
  }
  await browser.close();
})();
