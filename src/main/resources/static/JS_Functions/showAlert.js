function showAlert(message) {
    const alertDialog = document.getElementById('alert-dialog');
    const alertMessage = document.getElementById('alert-message');

    alertMessage.textContent = message;
    alertDialog.showModal();

    setTimeout(function() {
        alertDialog.close();
    }, 3000);
}
