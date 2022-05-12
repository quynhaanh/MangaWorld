<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idNovel = $_POST['IDNovel'];
    $query = "SELECT * FROM chapter WHERE ID_Novel = '$idNovel'";
    $result = mysqli_query($connect, $query);
    $total_records = mysqli_num_rows($result);

    if ($total_records > 0) {
        while ($row = mysqli_fetch_assoc($result)) {
            $temp_array[] = $row;
        }
    }
    echo json_encode(["chapters" => $temp_array], JSON_UNESCAPED_UNICODE);
    mysqli_close($connect);
}
