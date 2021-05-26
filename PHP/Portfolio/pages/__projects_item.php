<section>
    <!-- code icon -->
    <code class="bg-code">&#60;/&#62;</code>
    <!-- project name -->
    <h4>
        <?php echo $projects_item_name ?>
    </h4>
    <!-- image -->
    <img alt="image" class="project-img" src="<?php echo $projects_item_img ?>" />
    <!-- description -->
    <p>
        <?php echo $projects_item_desc ?>
    </p>
    <!-- technologies -->
    <p class="technologies">
        <?php echo $projects_item_technologies ?>
    </p>
    <!-- optional button to see more -->
    <?php
    if (strlen($projects_item_link) > 0) {
        echo '<a href="' . $projects_item_link . '">See more</a>';
    }
    ?>
</section>