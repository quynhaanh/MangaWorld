<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idUser = $_POST['IDUser'];
    $query = "SELECT * FROM novel WHERE ID_User = '$idUser'";
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
