<?php
require_once('console_log.php');
require_once('db.php');
require_once('comment.php');

function login($username, $password)
{
    $connection = connect();
    if ($connection != null) {
        try {
            // check if credential are ok
            $query = "SELECT * FROM users WHERE username = \"$username\" AND password = \"$password\"";
            foreach ($connection->query($query) as $row) {
                session_start();
                $_SESSION["loggedin"] = true;
                $_SESSION["id"] = $row['id'];
                $_SESSION["username"] = $username;
                header("Refresh:0");
                return true;
            }
        } catch (PDOException $e) {
            console_log("Błąd wywołania $query: " . $e->getMessage());
        }
    }
    alert("Unsuccessfull login");
    return false;
}

function logout()
{
    session_start();
    $_SESSION = array();
    session_destroy();
    header("Refresh:0");
}

function register($username, $password)
{
    $connection = connect();
    if ($connection != null) {
        try {
            // register a new user
            $query = "INSERT INTO users (username, password) VALUES (\"$username\", \"$password\")";
            $connection->query($query);
            // login
            $query = "SELECT * FROM users WHERE username = \"$username\" AND password = \"$password\"";
            foreach ($connection->query($query) as $row) {
                session_start();
                $_SESSION["loggedin"] = true;
                $_SESSION["id"] = $row['id'];
                $_SESSION["username"] = $username;
                header("Refresh:0");
                return true;
            }
        } catch (PDOException $e) {
            console_log("Błąd wywołania $query: " . $e->getMessage());
        }
    }
    alert("Unsuccessfull register");
    return false;
}


function waitForForms()
{
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        console_log($_POST['submit']);
        if ($_POST['submit'] == "login") {
            $username = $_POST['username'];
            $password = $_POST['password'];
            if (!empty(trim($username)) && !empty(trim($password))) {
                login($username, $password);
            }
        } else if ($_POST['submit'] == "register") {
            $username = $_POST['username'];
            $password = $_POST['password'];
            if (!empty(trim($username)) && !empty(trim($password))) {
                register($username, $password);
            }
        } else if ($_POST['submit'] == "logout") {
            logout();
        } else if ($_POST['submit'] == "add_comment") {
            $text = $_POST['new_comment'];
            $project = $_POST['project'];
            addComment($project, $text);
        }
    }
}
