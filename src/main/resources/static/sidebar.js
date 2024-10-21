document.addEventListener('DOMContentLoaded', function() {
    // 创建侧边栏容器
    const sidebar = document.createElement('div');
    sidebar.id = 'sidebar';
    sidebar.setAttribute('data-role', 'panel');
    sidebar.setAttribute('data-display', 'overlay');
    sidebar.setAttribute('data-position', 'left');
    sidebar.style.cssText = 'padding: 20px;';

    // 获取index.html的内容
    fetch('index.html')
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const mainContent = doc.querySelector('[data-role="main"]');
            
            if (mainContent) {
                const menu = document.createElement('div');
                menu.innerHTML = mainContent.innerHTML;
                
                // 移除不需要的元素
                const elementsToRemove = menu.querySelectorAll('div.ui-content, div[data-role="footer"]');
                elementsToRemove.forEach(el => el.remove());
                
                sidebar.appendChild(menu);
                
                // 将侧边栏添加到页面
                document.body.insertBefore(sidebar, document.body.firstChild);
                
                // 初始化侧边栏
                $(document).ready(function() {
                    $("#sidebar").panel();
                    
                    // 添加打开侧边栏的按钮
                    $("[data-role='header']").prepend('<a href="#sidebar" class="ui-btn ui-icon-bars ui-btn-icon-notext">打开菜单</a>');
                });
            }
        });
});

function loadContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const content = doc.querySelector('[data-role="main"]');
            if (content) {
                document.querySelector('[data-role="main"]').innerHTML = content.innerHTML;
            }
        });
}
