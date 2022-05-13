<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    $id = $_POST['ID'];
    $name = $_POST['Name'];

    if ($type == "insert") {
        $query = "INSERT INTO author VALUES ($id,'$name')";
    }
    if ($type == "delete") {
        $query = "DELETE FROM author WHERE ID = '$id'";
    }
    if ($type == "delete_all") {
        $query = "DELETE FROM author";
    }
    if ($type == "update") {
        $query = "UPDATE author SET Author_Name = '$name' WHERE ID = '$id'";
    }

    if (mysqli_query($connect, $query)) {
        echo json_encode('Success');
    } else {
        echo json_encode("Error");
    }

    mysqli_close($connect);
}
