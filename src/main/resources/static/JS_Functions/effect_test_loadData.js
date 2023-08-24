//页面分页的全局变量
const rowsPerPage = 8; // 每页显示的行数
let currentPage = 1; // 当前页号

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

            const startIndex = (currentPage - 1) * rowsPerPage;
            const endIndex = Math.min(startIndex + rowsPerPage, data.content.length);



            for (let i = startIndex; i < endIndex; i++) {

                // 注意这里，我们使用了data.content来遍历数组
                    const item = data.content[i]; // 这里是修改后的代码，将单个元素赋值给item
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
                   createTimeCell.textContent = formatDate(item.createTime); // 注意：这里是createTime，首字母C是大写



                // 当创建一个新的"参数设置"按钮时
                    const paramSettingCell = newRow.insertCell();
                    const paramSettingButton = document.createElement("button");
                    paramSettingButton.innerText = "参数设置";


                    // 在这里为刚刚创建的"参数设置"按钮添加事件监听器
                    paramSettingButton.addEventListener("click", function () {
                        const jobname = this.closest('tr').querySelector('td:nth-child(3)').textContent; // 第3列是任务名称

                        // 设置标志为true表示为编辑模式
                        isEditofEffect = true;

                        openUnitDialogWithJobName(jobname, isEditofEffect);
                    });
                    paramSettingCell.appendChild(paramSettingButton);


                    // 前置依赖按钮
                    const dependencyCell = newRow.insertCell();
                    const uploadQuestionButton = document.createElement("button");
                    uploadQuestionButton.innerText = "上传标准问和标准答";

                    //上传标准问和标准答
                    uploadQuestionButton.addEventListener("click", function (event) {
                        const currentRow = event.target.parentElement.parentElement; // 找到当前行
                        const jobNameCell = currentRow.cells[2]; // jobname是第2个单元格
                        const jobname = jobNameCell.textContent;

                        const fileInput = document.createElement("input");
                        fileInput.type = "file";
                        fileInput.accept = ".csv, .xlsx";
                        fileInput.style.display = "none";

                        //核心代码-开始
                        fileInput.addEventListener("change", function () {
                            const file = this.files[0];
                            if (file) {
                                if (file.size > 100 * 1024 * 1024) {
                                    alert("文件大小超过100MB，请选择一个更小的文件。");
                                    return;
                                }
                                const reader = new FileReader();
                                reader.onload = function (event) {
                                    let data;

                                    if (file.name.endsWith('.csv')) {
                                        const content = event.target.result.split('\n');
                                        data = content.slice(1).map(line => {
                                            const [standard_query, standard_answer] = line.split(','); // Assuming comma as delimiter
                                            return {jobname, standard_query, standard_answer};
                                        });
                                    } else if (file.name.endsWith('.xlsx')) {
                                        const workbook = XLSX.read(event.target.result, {type: 'binary'});
                                        const worksheet = workbook.Sheets[workbook.SheetNames[0]];
                                        const rows = XLSX.utils.sheet_to_json(worksheet, {header: ['standard_query', 'standard_answer']});
                                        data = rows.map(row => {
                                            return {
                                                jobname,
                                                standard_query: row.standard_query,
                                                standard_answer: row.standard_answer
                                            };
                                        });
                                    }

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
                                reader.onerror = function (error) {
                                    console.error('读取文件过程中出错:', error);
                                };
                                if (file.name.endsWith('.csv')) {
                                    reader.readAsText(file, 'UTF-8');
                                } else if (file.name.endsWith('.xlsx')) {
                                    reader.readAsBinaryString(file);
                                }
                            } else {
                                alert('请先选择一个文件！');
                            }
                        });

                        //核心代码-结束


                        document.body.appendChild(fileInput); // 这样用户才能看到并点击它
                        fileInput.click(); // 自动打开文件选择对话框
                    });
                    //上传标准问和标准答结束
                    dependencyCell.appendChild(uploadQuestionButton);




                // 操作按钮-开始
                const operationCell = newRow.insertCell();
                operationCell.style.display = 'flex';
                operationCell.style.flexDirection = 'column';
                operationCell.style.justifyContent = 'space-between';
                operationCell.style.paddingLeft = '50px';  // 设置左内边距为50px


                // 创建包含"批跑执行"和"效果查看"按钮的<div>

                // 批跑执行-开始

                const div1 = document.createElement('div');
                div1.style.display = 'flex';
                div1.style.justifyContent = 'flex-start';

                const testTaskButton = document.createElement("button");
                testTaskButton.innerText = "批跑执行";


                testTaskButton.onclick = function() {
                    // 显示对话框
                    document.getElementById('modal').style.display = 'block';

                    // 获取要执行的行
                    const rowToExecute = this.closest("tr");
                    // 获取任务名称的值
                    const taskNameCell = rowToExecute.cells[2]; // 假设任务名称是第2个单元格
                    const taskName = taskNameCell.textContent;

                    // 创建一个JSON对象并将任务名称加入到其中
                    const jsonBody = {
                        "taskName": taskName
                    };

                    // 发送POST请求到后端接口
                    fetch("/unit_effect/startUnitVerify", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(jsonBody)
                    }).then(response => response.json()).then(data => {
                        console.log("Received response from backend:", data);
                    }).catch(error => {
                        console.error("Error sending request to backend:", error);
                    });

                    var socket = new WebSocket("ws://localhost:8080/ws-endpoint");
                    socket.onmessage = function(event) {
                        console.log("Received message: " + event.data); // 添加这一行
                        document.getElementById('log').textContent += event.data + '\n';
                    };
                    socket.onopen = function(e) {
                        console.log("WebSocket opened"); // 添加这一行
                        socket.send(taskName);
                    };

                    socket.onmessage = function(event) {
                        console.log("Received message: " + event.data); // 添加这一行
                        document.getElementById('log').textContent += event.data + '\n';
                    };

                    socket.onerror = function(error) {
                        console.log("WebSocket error: " + error); // 添加这一行
                        alert(`WebSocket Error: ${error}`);
                    };
                }



                div1.appendChild(testTaskButton);


                // 批跑执行-结束





                //效果查看-开始

                const viewEffectButton = document.createElement("button");
                viewEffectButton.innerText = "效果查看";
                viewEffectButton.onclick = function() {
                    fetch('/unit_effect/getEffectData')
                        .then(response => response.json())
                        .then(data => {
                            const content = document.getElementById('effect-content');
                            content.innerHTML = `
                <p>批次号: ${data.maxRunBatchNo}</p>
                <p>批跑知识量总数: ${data.totalCount}</p>
                <p>比对成功总数: ${data.successCount}</p>
                <p>比对失败总数: ${data.failureCount}</p>
                <p>比对百分比: ${data.percentage.toFixed(2)}%</p>
            `;
                            document.getElementById('effect-modal').style.display = 'block';
                        })
                        .catch(error => {
                            console.error('Error fetching data:', error);
                            alert('获取效果查看数据时发生错误！');
                        });
                };
                viewEffectButton.style.marginLeft = "50px";  // 设置间隔为50px
                div1.appendChild(viewEffectButton);

                operationCell.appendChild(div1);


                ////效果查看-结束

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


                // 删除任务--开始

                const deleteTaskButton = document.createElement("button");
                deleteTaskButton.innerText = "删除任务";
                deleteTaskButton.onclick = function (event) {
                    // 获取要删除的行
                    const rowToDelete = this.closest("tr");
                    // 获取jobname的值
                    const jobNameCell = rowToDelete.cells[2]; // jobname是第2个单元格
                    const jobname = jobNameCell.textContent;

                    // 弹出确认对话框
                    const isConfirmed = confirm(`确认要删除任务：${jobname}吗？`);
                    if (!isConfirmed) {
                        return; // 如果用户点击“取消”按钮，则直接返回
                    }


                    // 发送HTTP DELETE请求到后端
                    fetch(`/deleteTask?jobname=${jobname}`, {
                        method: 'DELETE',
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.text(); // 使用response.text()代替response.json()
                        })
                        .then(data => {
                            console.log(data);
                            // 从前端表格中删除该行
                            const parent = rowToDelete.parentNode;
                            parent.removeChild(rowToDelete);
                            // 显示成功消息
                            alert('删除任务成功');
                        })
                        .catch(error => {
                            // 显示错误消息
                            alert('删除失败: ' + error);
                        });
                };

                // 删除任务--结束



                deleteTaskButton.style.marginLeft = "50px";  // 设置间隔为50px
                div2.appendChild(deleteTaskButton);

                operationCell.appendChild(div2);

                // 设置两组按钮之间的间隔为10px
                div2.style.marginTop = "10px";


                // 操作按钮-结束


                // ... 如果还有其他单元格，继续添加 ...



            }


            // 更新页码信息
            const pageInfo = document.getElementById("pageInfo");
            const totalPages = Math.ceil(data.content.length / rowsPerPage);
            pageInfo.textContent = currentPage + "/" + totalPages;


            document.getElementById("previousPage").addEventListener("click", function() {
                if (currentPage > 1) {
                    currentPage--;
                    effect_test_loadData(apiPath);
                }
            });

            document.getElementById("nextPage").addEventListener("click", function() {
                if (currentPage * rowsPerPage < data.content.length) {
                    currentPage++;
                    effect_test_loadData(apiPath);
                }
            });




        })
        .catch(error => {
            console.log('Fetch error: ', error);
        });
}
