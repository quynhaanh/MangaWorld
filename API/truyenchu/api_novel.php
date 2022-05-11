<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    $id = $_POST['ID'];
    $title = $_POST['Title'];
    $id_author = $_POST['IDAuthor'];
    $cover = $_POST['Cover'];
    $date_post = $_POST['DatePost'];
    $id_user = $_POST['IDUser'];

    if ($type == "insert") {
        $query = "INSERT INTO novel VALUES (null,'$title','$id_author','$cover','$date_post','$id_user')";
    }
    if ($type == "delete") {
        $query = "DELETE FROM novel WHERE ID = '$id'";
    }
    if ($type == "delete_all") {
        $query = "DELETE FROM novel";
    }
    if ($type == "update") {
        $query = "UPDATE novel SET Title = '$title', ID_Author = '$id_author', Cover='$cover' WHERE ID = '$id'";
    }

    if (mysqli_query($connect, $query)) {
        echo json_encode('Success');
    } else {
        echo json_encode("Error");
    }

    mysqli_close($connect);
}
