function effect_test_loadData(apiPath) {
    console.log("effect_test_loadData function is called with URL:", apiPath);
    fetch(apiPath)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const table = document.getElementById('product-table');

            // 首先，清空表格的当前数据
            while (table.rows.length > 1) {
                table.deleteRow(1);
            }

// ...

// 注意这里，我们使用了data.content来遍历数组
            data.content.forEach(item => {
                const newRow = table.insertRow();

                const selectCell = newRow.insertCell();
                const selectCheckbox = document.createElement('input');
                selectCheckbox.type = 'checkbox';
                selectCheckbox.name = 'productCheckbox';  // 确保添加这一行
                selectCell.appendChild(selectCheckbox);

                const productNameCell = newRow.insertCell();
                productNameCell.textContent = item.productName; // 注意：这里是productName，首字母P是大写

                const jobNameCell = newRow.insertCell();
                jobNameCell.textContent = item.jobname;

                const createTimeCell = newRow.insertCell();
                createTimeCell.textContent = item.createTime; // 注意：这里是createTime，首字母C是大写


                // 当创建一个新的"参数设置"按钮时
                const paramSettingCell = newRow.insertCell();
                const paramSettingButton = document.createElement("button");
                paramSettingButton.innerText = "参数设置";


                // 在这里为刚刚创建的"参数设置"按钮添加事件监听器
                paramSettingButton.addEventListener("click", function() {
                    const jobname = this.closest('tr').querySelector('td:nth-child(3)').textContent; // 第3列是任务名称

                    // 设置标志为true表示为编辑模式
                    isEditofEffect = true;

                    openUnitDialogWithJobName(jobname,isEditofEffect);
                });
                paramSettingCell.appendChild(paramSettingButton);



                // 前置依赖按钮
                const dependencyCell = newRow.insertCell();
                const uploadQuestionButton = document.createElement("button");
                uploadQuestionButton.innerText = "上传标准问";

                //上传标准问
                uploadQuestionButton.addEventListener("click", function(event) {

                    const currentRow = event.target.parentElement.parentElement; // 找到当前行
                    const jobNameCell = currentRow.cells[2]; // jobname是第2个单元格
                    const jobname = jobNameCell.textContent;

                    const fileInput = document.createElement("input");
                    fileInput.type = "file";
                    fileInput.accept = ".txt";
                    fileInput.style.display = "none";
                    fileInput.addEventListener("change", function() {
                        const file = this.files[0];
                        if (file) {
                            if (file.size > 100 * 1024 * 1024) {
                                alert("文件大小超过100MB，请选择一个更小的文件。");
                                return;
                            }
                            const reader = new FileReader();
                            reader.onload = function(event) {
                                const content = event.target.result.split('\n');
                                const data = content.map(query => ({ jobname, query }));
                                fetch('/uploadStandardQuestion', {
                                    method: 'POST',
                                    body: JSON.stringify(data),
                                    headers: {
                                        'Content-Type': 'application/json'
                                    }
                                })
                                    .then(response => response.json())
                                    .then(data => {
                                        if (data.success) {
                                            alert('上传成功！');
                                        } else {
                                            alert('上传失败：' + data.message);
                                        }
                                    })
                                    .catch(error => {
                                        console.error('上传过程中出错:', error);
                                    });
                            };
                            reader.onerror = function(error) {
                                console.error('读取文件过程中出错:', error);
                            };
                            reader.readAsText(file, 'UTF-8');
                        } else {
                            alert('请先选择一个文件！');
                        }
                    });
                    document.body.appendChild(fileInput); // 这样用户才能看到并点击它
                    fileInput.click(); // 自动打开文件选择对话框
                });
                //上传标准问结束


                const uploadAnswerButton = document.createElement("button");
                uploadAnswerButton.innerText = "上传标准答";
                dependencyCell.appendChild(uploadQuestionButton);
                dependencyCell.appendChild(uploadAnswerButton);


                // 操作按钮
                const operationCell = newRow.insertCell();
                const testTaskButton = document.createElement("button");
                testTaskButton.innerText = "测试任务";
                const deleteTaskButton = document.createElement("button");
                deleteTaskButton.innerText = "删除任务";
                operationCell.appendChild(testTaskButton);
                operationCell.appendChild(deleteTaskButton);

                // ... 如果还有其他单元格，继续添加 ...
            });

        })
        .catch(error => {
            console.log('Fetch error: ', error);
        });
}
