<main class="projects">
    <!-- background image -->
    <img alt="image" class="project-desc-img" src="<?php echo $projects_item_img ?>" />
    <!-- project details -->
    <section>
        <!-- code icon -->
        <code class="bg-code">&#60;/&#62;</code>
        <!-- project name -->
        <h4 class="title">
            <?php echo $projects_item_name ?>
        </h4>
        <!-- long description -->
        <p>
            <?php echo $projects_item_desc ?>
        </p>
        <p>
            <?php echo $projects_item_desc ?>
        </p>
        <!-- technologies -->
        <p class="technologies">
            <?php echo $projects_item_technologies ?>
        </p>
    </section>
    <!-- code -->
    <section>
        <h4>Code</h4>
        <code class="bg-code">&#60;/&#62;</code>
        <code class="code">
            <?php echo $projects_item_code ?>
        </code>
        <a href="<?php echo $projects_item_full_code_link ?>" target="_blank">See full code</a>
    </section>
    <!-- comments -->
    <section>
        <h4>Comments</h4>
        <code class="bg-code">&#60;/&#62;</code>
        <!-- show comments -->
        <?php
        require_once(APP_PATH . 'functions/comment.php');
        $comments = getComments($projects_item_name);
        if (count($comments) == 0) {
            echo "<div class=\"comments empty\"><p>Be the first one to comment</p></div>";
        } else {
            echo "<div class=\"comments\">";
            foreach ($comments as $row) {
                echo "<p class=\"author\">[" . $row['author'] . "]</p><p class=\"text\">" . $row['text'] . "</p>";
            }
            echo "</div>";
        }
        ?>
        <!-- login / logout / register -->
        <div class="user-btns">
            <?php
            require_once(APP_PATH . 'functions/account.php');
            session_start();
            console_log($_SESSION);
            if (isset($_SESSION["loggedin"]) && $_SESSION["loggedin"]) {
                // show adding comment adn logout button
                echo "
                <form action=\"#\" method=\"post\">
                    <input class=\"invisible\" type=\"text\" name=\"project\" value=\"$projects_item_name\">
                    <input type=\"text\" name=\"new_comment\">
                    <input type=\"submit\" name=\"submit\" value=\"add_comment\">
                    <input type=\"submit\" name=\"submit\" value=\"logout\">
                </form>";
            } else {
                // show login / register buttons
                echo "
                <form method=\"post\">
                    <input type=\"text\" name=\"username\" placeholder=\"username\" required>
                    <input type=\"password\" name=\"password\" placeholder=\"password\" required>
                    <input type=\"submit\" name=\"submit\" value=\"login\">
                    <input type=\"submit\" name=\"submit\" value=\"register\">
                </form>";
            }
            waitForForms();
            ?>
        </div>
    </section>
</main>