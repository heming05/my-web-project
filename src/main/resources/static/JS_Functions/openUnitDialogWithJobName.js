function openUnitDialogWithJobName(jobname, isEditofEffect) {
    const unitDialog = document.getElementById('unit-dialog');
    const jobnameInput = document.getElementById('jobname');

    console.log('Received jobName:', jobname);  // 打印传递的任务名称

    // 使用jobName查询后台数据库，获取相关信息并填充到对话框中
    fetch('/effect-test-get-data-with-jobName?jobName=' + jobname)
        .then(response => response.json())
        .then(data => {
            jobnameInput.value = data.jobname;
            document.getElementById('endpoint').value = data.endpoint;
            document.getElementById('api').value = data.api;
            document.getElementById('authorization').value = data.authorization;
            document.getElementById('parallel').value = data.parallel;

            // 如果是编辑模式，则禁用任务名称输入框
            jobnameInput.disabled = !!isEditofEffect;
        })
        .catch(error => {
            console.error("Error fetching data: ", error);
        });

    unitDialog.showModal();
}
