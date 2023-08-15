document.getElementById('unit-confirm-btn').addEventListener('click', function(e) {
    e.preventDefault();

    const productName = 'UNIT';
    const jobname = document.getElementById('jobname').value;
    const endpoint = document.getElementById('endpoint').value;
    const api = document.getElementById('api').value;
    const authorization = document.getElementById('authorization').value;
    const parallel = document.getElementById('parallel').value;
    const currentTime = new Date();
    const formattedTime = currentTime.toLocaleDateString() + " " + currentTime.toLocaleTimeString();

    // 根据isEditofEffect的值设置API路径和方法
    const apiUrl = isEditofEffect ? '/effect_test_update' : '/effect_test_params_set';
    const apiMethod = isEditofEffect ? 'PUT' : 'POST';

    fetch(apiUrl, {
        method: apiMethod,
        body: JSON.stringify({
            productName: productName,
            jobname: jobname,
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
            // 关闭主对话框
            document.getElementById('unit-dialog').close();

            if (!isEditofEffect) {
                const tableBody = document.getElementById('product-table');
                const newRow = tableBody.insertRow();

                // Checkbox cell
                const selectCell = newRow.insertCell();
                const selectCheckbox = document.createElement('input');
                selectCheckbox.type = 'checkbox';
                selectCheckbox.name = 'productCheckbox';
                selectCell.appendChild(selectCheckbox);

                // Product name cell
                const productNameCell = newRow.insertCell();
                productNameCell.innerText = productName;

                // Job name cell
                const jobNameCell = newRow.insertCell();
                jobNameCell.innerText = jobname;

                // Create time cell
                const createTimeCell = newRow.insertCell();
                createTimeCell.innerText = formattedTime;

                // Params setting cell
                const paramsSettingCell = newRow.insertCell();
                const paramsSettingButton = document.createElement('button');
                paramsSettingButton.innerText = '参数设置';
                paramsSettingButton.onclick = function() {
                    // Here you can add logic to edit the parameters, if needed
                }
                paramsSettingCell.appendChild(paramsSettingButton);

                // Pre Dependency cell
                const preDependencyCell = newRow.insertCell();
                const preDependencyButton1 = document.createElement('button');
                preDependencyButton1.innerText = '上传标准问';
                preDependencyButton1.onclick = function() {
                    // Here you can add logic for uploading standard questions
                }
                preDependencyCell.appendChild(preDependencyButton1);
                const preDependencyButton2 = document.createElement('button');
                preDependencyButton2.innerText = '上传标准答';
                preDependencyButton2.onclick = function() {
                    // Here you can add logic for uploading standard answers
                }
                preDependencyCell.appendChild(preDependencyButton2);

                // Operation cell
                const operationCell = newRow.insertCell();
                const testExecutionButton = document.createElement('button');
                testExecutionButton.innerText = '测试执行';
                testExecutionButton.onclick = function() {
                    // Here you can add logic to execute the test
                }
                operationCell.appendChild(testExecutionButton);
                const deleteTaskButton = document.createElement('button');
                deleteTaskButton.innerText = '删除任务';
                deleteTaskButton.onclick = function() {
                    // Here you can add logic to delete the task
                }
                operationCell.appendChild(deleteTaskButton);
            }


            // 显示成功的模态框
            let successDialog = document.createElement('dialog');
            successDialog.innerText = "数据更新成功";
            document.body.appendChild(successDialog);
            successDialog.showModal();

            // 1.5秒后关闭成功的模态框
            setTimeout(() => {
                successDialog.close();
                document.body.removeChild(successDialog);
            }, 1500);

            // ... (任何其他成功后的操作，例如更新表格)
        })
        .catch(error => {
            alert(error);
        });
});
