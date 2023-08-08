function openEditDialog(rowData) {
    isEditing = true; // 编辑时将编辑状态设为true
    document.getElementById('name').value = rowData.name;
    document.getElementById('name').disabled = true; // 禁用name输入框
    document.getElementById('description').value = rowData.description;
    document.getElementById('host').value = rowData.host;
    document.getElementById('port').value = rowData.port;
    document.getElementById('username').value = rowData.username;
    document.getElementById('password').value = rowData.password;

    // 保存当前编辑行的数据源名称
    document.getElementById('dialog').dataset.editingName = rowData.name;
    document.getElementById('dialog').showModal();
}