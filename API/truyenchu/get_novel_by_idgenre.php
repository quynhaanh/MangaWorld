<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idGenre = $_POST['IDGenre'];
    $query = "SELECT * FROM novel, novel_genres WHERE novel_genres.ID_Genre = '$idGenre' AND novel_genres.ID_Novel = novel.ID";
    $result = mysqli_query($connect, $query);
    $total_records = mysqli_num_rows($result);

    if ($total_records > 0) {
        while ($row = mysqli_fetch_assoc($result)) {
            $temp_array[] = $row;
        }
    }
    echo json_encode(["novels" => $temp_array], JSON_UNESCAPED_UNICODE);
    mysqli_close($connect);
}
