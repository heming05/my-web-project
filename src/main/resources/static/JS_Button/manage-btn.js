document.getElementById('manage-btn').addEventListener('click', function() {
    document.getElementById('data-management-content').style.display = 'block';
    document.getElementById('test-effect-content').style.display = 'none';
    // 这里可以添加源数据管理特定的逻辑
    document.getElementById('ssh-connections-table').style.display = 'table'; // 显示表格
    loadData(0); // 从第一页开始加载
});