document.getElementById('effect-search-btn').addEventListener('click', function() {
    let query = document.getElementById('effect-search-input').value.trim().toLowerCase(); // 获取输入值并转换为小写以进行不区分大小写的匹配
    let table = document.getElementById('product-table');
    let rows = table.getElementsByTagName('tr');

    for (let i = 1; i < rows.length; i++) {  // 开始于1，以跳过thead中的行
        let cell = rows[i].getElementsByTagName('td')[2];  // '任务名称' 是第三个单元格，因此索引是2
        if (cell) {
            let text = cell.textContent || cell.innerText;
            if (text.toLowerCase().indexOf(query) > -1) {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
    }
});
