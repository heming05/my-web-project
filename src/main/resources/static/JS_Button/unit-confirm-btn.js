// 为对话框中的确定按钮添加事件监听器
document.getElementById('unit-confirm-btn').addEventListener('click', function(e) {
    e.preventDefault();

    const productName = 'UNIT';
    const jobName = document.getElementById('jobname').value;
    const endpoint = document.getElementById('endpoint').value;
    const api = document.getElementById('api').value;
    const authorization = document.getElementById('authorization').value;
    const parallel = document.getElementById('parallel').value;

    const currentTime = new Date();
    const formattedTime = currentTime.toLocaleDateString() + " " + currentTime.toLocaleTimeString();

    fetch('/effect_test_params_set', {
        method: 'POST',
        body: JSON.stringify({
            productName: productName,
            jobname: jobName,
            endpoint: endpoint,
            api: api,
            authorization: authorization,
            parallel: parallel,
            create_time: formattedTime,
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else if (response.status === 409) {
                throw new Error('任务名称已存在，请使用一个新的任务名称。');
            } else {
                throw new Error('发生错误，请稍后再试。');
            }
        })
        .then(data => {
            const tableBody = document.getElementById('product-table-body');
            const newRow = tableBody.insertRow();

            const selectCell = newRow.insertCell();
            const selectCheckbox = document.createElement('input');
            selectCheckbox.type = 'checkbox';
            selectCheckbox.name = 'productCheckbox';  // 设置名称
            selectCell.appendChild(selectCheckbox);

            const productNameCell = newRow.insertCell();
            productNameCell.innerText = productName;

            const jobNameCell = newRow.insertCell();
            jobNameCell.innerText = jobName;

            const createTimeCell = newRow.insertCell();
            createTimeCell.innerText = formattedTime;

            const paramsSettingCell = newRow.insertCell();
            const paramsSettingButton = document.createElement('button');
            paramsSettingButton.innerText = '参数设置';
            paramsSettingButton.onclick = function() {}
            paramsSettingCell.appendChild(paramsSettingButton);

            const preDependencyCell = newRow.insertCell();
            const preDependencyButton1 = document.createElement('button');
            preDependencyButton1.innerText = '上传标准问';
            preDependencyButton1.onclick = function() {}
            preDependencyCell.appendChild(preDependencyButton1);
            const preDependencyButton2 = document.createElement('button');
            preDependencyButton2.innerText = '上传标准答';
            preDependencyButton2.onclick = function() {}
            preDependencyCell.appendChild(preDependencyButton2);

            const operationCell = newRow.insertCell();
            const testExecutionButton = document.createElement('button');
            testExecutionButton.innerText = '测试执行';
            testExecutionButton.onclick = function() {}
            operationCell.appendChild(testExecutionButton);
            const deleteTaskButton = document.createElement('button');
            deleteTaskButton.innerText = '删除任务';
            deleteTaskButton.onclick = function() {}
            operationCell.appendChild(deleteTaskButton);

            // 关闭对话框
            document.getElementById('unit-dialog').close();
        })
        .catch(error => {
            alert(error);
        });
});
