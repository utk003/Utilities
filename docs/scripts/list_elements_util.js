// single function which performs site pre-processing
// and enables on-click callbacks for all necessary buttons
$(function () {
    const sections = ["rnd", "libs", "clss"];                   // list of all lists in html code
    const modifiers = ["l3", "l2", "l1", "r1", "r2", "r3"];     // id codes for arrows (for list control)
    const shifts = [-100000, -3, -1, 1, 3, 100000];             // corresponding shifts of modifiers
    for (let i = sections.length - 1; i >= 0; i--) {
        // get number of elements in list
        const numElements = $("#" + sections[i]).children().length;

        // set number of elements in listsize var (to display)
        $("#" + sections[i] + "size").text("(" + numElements + ")")

        // hide all elements in each list past 3rd (can display max of 3 at a time)
        for (let j = 4; j <= numElements; j++)
            $("#" + sections[i] + j).addClass("hide");

        // hide the collapse button - only "show" should initially be visible
        const collapseJQueryElement = $("#" + sections[i] + "hide");
        collapseJQueryElement.addClass("hide");

        // set up 6 arrow buttons for list display control
        for (let j = 0; j < 6; j++)
            $("#" + sections[i] + modifiers[j]).click(function () {
                // only allow arrow functionality if in show mode with > 3 elements in list
                if (numElements > 3 && collapseJQueryElement.hasClass("hide")) {
                    // locate index of first visible element
                    let ind = 1;
                    for (; ind <= numElements; ind++)
                        if (!$("#" + sections[i] + ind).hasClass("hide"))
                            break;
                    // shift index according to shifts array, and bound to [1, numElements]
                    ind += shifts[j];
                    if (ind < 1)
                        ind = 1;
                    else if (ind > numElements - 2)
                        ind = numElements - 2;
                    // hide all elements except for the <= 3 which should be shown
                    for (let k = 1; k <= numElements; k++)
                        $("#" + sections[i] + k).addClass("hide");
                    for (let k = 0; k < 3; k++)
                        $("#" + sections[i] + (ind + k)).removeClass("hide");
                }
            });

        const showJQueryElement = $("#" + sections[i] + "show");
        // configure show button to reveal all elements of list
        showJQueryElement.click(function () {
            // show all elements in list
            for (let k = 1; k <= numElements; k++)
                $("#" + sections[i] + k).removeClass("hide");

            // hide show button and show collapse button
            showJQueryElement.addClass("hide");
            collapseJQueryElement.removeClass("hide");
        });

        // configure collapse button to hide all but first <= 3 elements of list
        collapseJQueryElement.click(function () {
            // hide all elements in list except for first <= 3
            for (let k = 1; k <= numElements; k++)
                $("#" + sections[i] + k).addClass("hide");
            for (let k = 1; k <= 3; k++)
                $("#" + sections[i] + k).removeClass("hide");

            // show show button and hide collapse button
            collapseJQueryElement.addClass("hide");
            showJQueryElement.removeClass("hide");
        });
    }
})