//判断数据源的name是否存在
function isNameExist(name) {
    const tableBody = document.getElementById('ssh-connections-table-body');
    const rows = tableBody.querySelectorAll('tr');

    for (let row of rows) {
        const cellName = row.cells[1].innerText;
        if (cellName === name) return true;
    }

    return false;
}