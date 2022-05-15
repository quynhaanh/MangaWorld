<?php

require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    $id = $_POST['ID'];

    $query = "UPDATE novel SET View = View + 1 WHERE ID = '$id'";

    if (mysqli_query($connect, $query)) {
        echo json_encode('Success');
    } else {
        echo json_encode("Error");
    }

    mysqli_close($connect);
}
