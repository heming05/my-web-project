
    let isEditing = false; // 在全局范围内引入状态变量


    document.getElementById('select-all').addEventListener('change', function() {
        const checkboxes = document.querySelectorAll('#ssh-connections-table tbody input[type="checkbox"]');
        checkboxes.forEach(checkbox => checkbox.checked = this.checked);
});



    //新建源连接
    document.getElementById('new-btn').addEventListener('click', function() {
    isEditing = false; // 新建时将编辑状态设为false
    document.getElementById('name').disabled = false; // 启用name输入框
    document.getElementById('dialog').showModal();
    document.getElementById('name').blur(); // 这将移除'名称'输入框的焦点
});



    document.getElementById('test-btn').addEventListener('click', async function() {
    const host = document.getElementById('host').value;
    const port = document.getElementById('port').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    console.log('开始进行SSH连接测试');
    console.log('host:', host, 'port:', port, 'username:', username, 'password:', password);

    const response = await fetch('/test-ssh', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ host, port, username, password })
});


    if (response.ok) {
    //const { status } = await response.json();
    showAlert('ok，连接成功');
} else {
    showAlert('对不起，请求失败');
}

});

//效果测试的代码从这里开始
    // 上一步按钮的事件监听器
    document.getElementById('previous-step-button').addEventListener('click', function() {
        // 关闭UNIT对话框
        document.getElementById('unit-dialog').close();

        // 打开产品选择对话框
        document.getElementById('product-dialog').showModal();
    });


    // 初始化全局变量
    let selectedProduct = "";

    // 为每个按钮添加点击事件处理器
    const buttons = document.querySelectorAll(".product-button");
    buttons.forEach(function(button) {
        button.addEventListener("click", function() {
            selectedProduct = this.innerText;  // 更新全局变量
        });
    });

    // 初始化全局变量
    let isEditofEffect = false; // 在全局范围内引入状态变量








