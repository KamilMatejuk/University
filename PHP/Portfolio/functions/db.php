<?php
require_once('console_log.php');

// connect to database
function connect()
{
    $servername = "localhost";
    $username = "username";
    $password = "password";
    $database = "www";
    try {
        $conn = new PDO(
            "mysql:host=$servername;dbname=$database",
            $username,
            $password
        );
        // set the PDO error mode to exception
        $conn->setAttribute(
            PDO::ATTR_ERRMODE,
            PDO::ERRMODE_EXCEPTION
        );
        console_log("Połączenie poprawne");
        return $conn;
    } catch (PDOException $e) {
        console_log("Błąd połaczenia: " . $e->getMessage());
        return null;
    }
}

// count different ips logged in today
function addToCounter($ip)
{
    $connection = connect();
    $count = 0;
    if ($connection != null) {
        try {
            // delete all not from today
            $query = "DELETE FROM ips WHERE date != CURDATE()";
            $connection->query($query);
            // count current visits
            $query = "SELECT COUNT(*) as cnt FROM ips";
            foreach ($connection->query($query) as $row) {
                $count = $row['cnt'];
            }
            // check if ip already in database
            $query = "SELECT ip FROM ips WHERE ip = \"$ip\"";
            foreach ($connection->query($query) as $row) {
                return $count;
            }
            // add ip to database
            $query = "INSERT INTO ips (ip, date) VALUES (\"$ip\", CURDATE())";
            $connection->query($query);
            // return new number of visits
            return $count + 1;
        } catch (PDOException $e) {
            console_log("Błąd wywołania $query: " . $e->getMessage());
            return $count;
        }
    } else {
        return $count;
    }
}
