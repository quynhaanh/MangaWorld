<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    $id = $_POST['ID'];
    $name = $_POST['Name'];
    $pass = $_POST['Pass'];
    $email = $_POST['Email'];
    $id_role = $_POST['IDRole'];

    if ($type == "insert") {
        $query = "INSERT INTO user VALUES ('$id','$name','$email','$pass','$id_role')";
    }
    if ($type == "delete") {
        $query = "DELETE FROM user WHERE ID = '$id' AND Pass = '$pass'";
    }
    if ($type == "delete_all") {
        $query = "DELETE FROM user";
    }
    if ($type == "update") {
        $query = "UPDATE user SET Name = '$name', Pass = '$pass' WHERE ID = '$id'";
    }

    if (mysqli_query($connect, $query)) {
        echo json_encode('Success');
    } else {
        echo json_encode("Error");
    }

    mysqli_close($connect);
}
