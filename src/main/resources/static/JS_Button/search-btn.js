document.getElementById('search-btn').addEventListener('click', function() {
    var searchText = document.getElementById('search-input').value;
    var tableBody = document.getElementById('ssh-connections-table-body');
    var rows = tableBody.querySelectorAll('tr');

    // 遍历表格中的每一行
    rows.forEach(function(row) {
        var nameCell = row.cells[1]; // 假设名称在第二列
        var nameText = nameCell.textContent || nameCell.innerText;

        // 如果名称与搜索文本不匹配，则隐藏该行；否则显示
        if (nameText.indexOf(searchText) === -1) {
            row.style.display = 'none';
        } else {
            row.style.display = '';
        }
    });
});
