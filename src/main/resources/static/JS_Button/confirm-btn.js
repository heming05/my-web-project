document.getElementById('confirm-btn').addEventListener('click', async function(event) {

    // ... 获取输入值的代码 ...
    const name = document.getElementById('name').value;

    if (!isEditing && isNameExist(name)) {
        event.preventDefault(); // 阻止表单提交
        alert('数据源名称已经存在，请重新输入');
        return; // 确保名称存在时不会继续执行以下代码
    }

    const description = document.getElementById('description').value;
    const host = document.getElementById('host').value;
    const port = document.getElementById('port').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (!name || !host || !port || !username || !password) {
        event.preventDefault(); // 阻止表单提交
        alert('请填写所有必填字段！');
        return;
    }

    let response;

    if (isEditing) {

        const name = document.getElementById('name').value;

        response = await fetch(`/update-datasource/${name}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                description: description,
                host: host,
                port: port,
                username: username,
                password: password
            })
        });
    } else {
        response = await fetch('/create-ssh', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                description: description,
                host: host,
                port: port,
                username: username,
                password: password
            })
        });
    }

    if (response.ok) {
        // 成功的响应，重新加载数据或插入新行到表格中
        if (isEditing) {
            // 更新操作成功
            loadData(0); // 重新加载数据，假设 loadData 是用来刷新表格的函数
        } else {
            // 创建操作成功
            const createdAt = new Date().toLocaleString();
            const tableBody = document.getElementById('ssh-connections-table-body');

            const row = tableBody.insertRow();
            row.insertCell(0).innerHTML = '<input type="checkbox">';
            row.insertCell(1).innerHTML = name;
            row.insertCell(2).innerHTML = createdAt;
            row.insertCell(3).innerHTML = description;

            const actionsCell = row.insertCell(4);
            actionsCell.innerHTML = '<button class="edit-btn">编辑</button> <button class="delete-btn">删除</button>';

            actionsCell.querySelector('.edit-btn').addEventListener('click', function() {
                openEditDialog({
                    name: name,
                    description: description,
                    host: host,
                    port: port,
                    username: username,
                    password: password
                });
            });

            actionsCell.querySelector('.delete-btn').addEventListener('click', function() {
                if (confirm('确定要删除吗?')) {
                    tableBody.deleteRow(row.rowIndex - 1);
                }
            });
        }
    } else {
        // 处理错误
        alert('保存SSH连接失败，请稍后重试。');
    }

    document.getElementById('dialog').close();
});