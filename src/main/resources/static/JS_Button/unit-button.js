// 产品选择--请选择对应产品--UNIT

document.getElementById('unit-button').addEventListener('click', function() {
    isEditofEffect = false; // 设置为新增模式
    resetDialog(); // 在显示对话框之前重置它的状态
    let unitDialog = document.getElementById('unit-dialog');
    unitDialog.showModal();
});

// resetDialog函数用于重置对话框的状态
function resetDialog() {
    // 清空所有的输入字段
    document.getElementById('jobname').value = '';
    document.getElementById('endpoint').value = '';
    document.getElementById('api').value = '';
    document.getElementById('authorization').value = '';
    document.getElementById('parallel').value = '';

    // 确保任务名称输入框是可编辑的
    document.getElementById('jobname').disabled = false;
}
