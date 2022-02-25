function registration() {
    window.location.href = 'http://localhost:8080/todo/reg.html'
}

function auth() {
    $.ajax( {
        type: 'POST',
        url: 'http://localhost:8080/todo/auth',
        data: JSON.stringify({
            name: $('#login').val(),
            password: $('#password').val()
        })
    }).fail(function () {
        alert('Ошибка!');
    })
}