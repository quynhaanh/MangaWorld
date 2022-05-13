<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    $id = $_POST['ID'];
    $title = $_POST['Title'];
    $content = $_POST['Content'];
    $date_post = date("Y-m-d H:i:s");
    $id_novel = $_POST['IDNovel'];

    if ($type == "insert") {
        $query = "INSERT INTO chapter VALUES ($id,'$title','$content','$date_post','$id_novel')";
    }
    if ($type == "delete") {
        $query = "DELETE FROM chapter WHERE ID = '$id'";
    }
    if ($type == "delete_all") {
        $query = "DELETE FROM chapter";
    }
    if ($type == "update") {
        $query = "UPDATE chapter SET Title = '$title', Content = '$content' WHERE ID = '$id'";
    }

    if (mysqli_query($connect, $query)) {
        echo json_encode('Success');
    } else {
        echo json_encode("Error");
    }

    mysqli_close($connect);
}
