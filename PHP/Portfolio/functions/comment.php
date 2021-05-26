<?php
require_once('db.php');

function getComments($project)
{
    $connection = connect();
    if ($connection != null) {
        try {
            $query = "SELECT author, text FROM comments WHERE project = \"$project\"";
            // return $connection->query($query);
            $results = [];
            foreach ($connection->query($query) as $row) {
                array_push($results, $row);
            }
            return $results;
        } catch (PDOException $e) {
            console_log("Błąd wywołania $query: " . $e->getMessage());
            return;
        }
    }
}

function addComment($project, $text)
{
    $connection = connect();
    if ($connection != null) {
        try {
            $query = "INSERT INTO comments(author, project, text) VALUES (\"" . $_SESSION["username"] . "\", \"$project\", \"$text\")";
            $connection->query($query);
            header("Refresh:0");
        } catch (PDOException $e) {
            console_log("Błąd wywołania $query: " . $e->getMessage());
            return;
        }
    }
}
