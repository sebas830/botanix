const searchInput = document.getElementById("searchInput");

if (searchInput) {
    searchInput.addEventListener("keyup", function () {
        let filtro = searchInput.value.toLowerCase();
        let tabla = document.querySelector(".custom-table");

        if (tabla) {
            let filas = tabla.querySelectorAll("tbody tr");
            filas.forEach(fila => {
                let texto = fila.textContent.toLowerCase();
                fila.style.display = texto.includes(filtro) ? "" : "none";
            });
        }
    });
}
