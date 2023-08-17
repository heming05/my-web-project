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
                const preDependencyButton = document.createElement('button');
                preDependencyButton.innerText = '上传标准问和标准答';
                preDependencyButton.onclick = function() {
                    // Here you can add logic for uploading standard questions
                }

                preDependencyCell.appendChild(preDependencyButton);


               // 操作-开始
                const operationCell = newRow.insertCell();
                operationCell.style.display = 'flex';
                operationCell.style.flexDirection = 'column';
                operationCell.style.justifyContent = 'space-between';
                operationCell.style.paddingLeft = '50px';  // 设置左内边距为50px

                // 创建包含"批跑执行"和"效果查看"按钮的<div>
                const div1 = document.createElement('div');
                div1.style.display = 'flex';
                div1.style.justifyContent = 'flex-start';

                const testTaskButton = document.createElement("button");
                testTaskButton.innerText = "批跑执行";
                testTaskButton.onclick = function() {
                    // Here you can add logic to execute the test task
                }
                div1.appendChild(testTaskButton);

                const viewEffectButton = document.createElement("button");
                viewEffectButton.innerText = "效果查看";
                viewEffectButton.onclick = function() {
                    // Here you can add logic to view the effect
                }
                viewEffectButton.style.marginLeft = "50px";  // 设置间隔为50px
                div1.appendChild(viewEffectButton);

                operationCell.appendChild(div1);

                // 创建包含"结果下载"和"删除任务"按钮的<div>
                const div2 = document.createElement('div');
                div2.style.display = 'flex';
                div2.style.justifyContent = 'flex-start';

                const downloadResultButton = document.createElement("button");
                downloadResultButton.innerText = "结果下载";
                downloadResultButton.onclick = function() {
                    // Here you can add logic to download the result
                }
                div2.appendChild(downloadResultButton);

                const deleteTaskButton = document.createElement("button");
                deleteTaskButton.innerText = "删除任务";
                deleteTaskButton.onclick = function() {
                    // Here you can add logic to delete the task
                }
                deleteTaskButton.style.marginLeft = "50px";  // 设置间隔为50px
                div2.appendChild(deleteTaskButton);

                operationCell.appendChild(div2);

                // 设置两组按钮之间的间隔为10px
                div2.style.marginTop = "10px";

               // 操作-结束



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
