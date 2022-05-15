<?php
require "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $type = $_POST['Type'];

    $id_novel = $_POST['IDNovel'];
    $id_genre = $_POST['IDGenre'];

    if ($type == "insert") {
        $query = "INSERT INTO novel_genres VALUES ('$id_novel','$id_genre')";
    }
    if ($type == "delete") {
        $query = "DELETE FROM novel_genres WHERE ID_Novel = '$id_novel' AND ID_Genre='$id_genre'";
    }
    if ($type == "delete_by_id_novel") {
        $query = "DELETE FROM novel_genres WHERE ID_Novel = '$id_novel'";
    }
    if ($type == "delete_all") {
        $query = "DELETE FROM novel_genres";
    }
    if ($type == "update") {
        $query = "UPDATE novel_genres SET ID_Genre = '$id_genre' WHERE ID_Novel = '$id_novel'";
    }

    if (mysqli_query($connect, $query)) {
        echo json_encode('Success');
    } else {
        echo json_encode("Error");
    }

    mysqli_close($connect);
}
