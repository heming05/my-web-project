async function loadData(pageNumber) {
    const response = await fetch(`/get-data?page=${pageNumber}&size=20`);
    const data = await response.json();

    // 清空现有表格数据
    const tableBody = document.getElementById('ssh-connections-table-body');
    tableBody.innerHTML = '';

    // 添加新数据
    data.content.forEach(row => {
        const tableRow = tableBody.insertRow();
        tableRow.insertCell(0).innerHTML = '<input type="checkbox">';
        tableRow.insertCell(1).innerHTML = row.name;
        tableRow.insertCell(2).innerHTML = new Date(row.createdAt).toLocaleString(); // 格式化日期
        tableRow.insertCell(3).innerHTML = row.description;
        const actionsCell = tableRow.insertCell(4);
        actionsCell.innerHTML = '<button class="edit-btn">编辑</button> <button class="delete-btn">删除</button>';

        actionsCell.querySelector('.edit-btn').addEventListener('click', function() {

            openEditDialog(row);
            document.getElementById('name').blur(); // 这将移除'名称'输入框的焦点

        });

        //数据源--删除按钮
        actionsCell.querySelector('.delete-btn').addEventListener('click', function() {

            console.log('Delete button clicked'); // 确保监听器触发
            const name =row.name;
            console.log('Name to delete:', name); // 确保获取了正确的名称

            if (confirm('确定要删除吗?')) {
                fetch(`/delete-ssh/${name}`, {
                    method: 'DELETE',
                })
                    .then(response => {
                        if (response.ok) {
                            tableBody.deleteRow(row.rowIndex - 1);
                        } else {
                            alert('删除数据源失败，请稍后重试。');
                        }
                    });
            }
        });

        // 添加事件监听器，用于编辑和删除功能
    });

    // 更新分页控制（如果有）
}