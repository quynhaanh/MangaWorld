<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['ID'];
    $query = "SELECT * FROM genre WHERE ID = '$id'";
    $result = mysqli_query($connect, $query);
    $total_records = mysqli_num_rows($result);

    if ($total_records > 0) {
        echo json_encode(mysqli_fetch_assoc($result), JSON_UNESCAPED_UNICODE);
    } else {
        echo "Error";
    }

    mysqli_close($connect);
}
