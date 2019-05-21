var counter = 3;
var limit = 100;

function addInput(divName){
     if (counter == limit)  {
          alert("Ai atins limita maxima de " + counter + " de perechi input/output.");
     }
     else {
          var newdiv = document.createElement('div');

          newdiv.innerHTML = "<h3 class=\"text\">Input " + (counter + 1) + " :</h3> <textarea placeholder=\"Input " + (counter + 1) + "\" name=\"input" + (counter + 1) + "\" id=\"solutie\" cols=\"50\" rows=\"10\"></textarea><h3 class=\"text\">Output " + (counter + 1) + "</h3><textarea placeholder=\"Output " + (counter + 1) + "\" name=\"output" + (counter + 1) + "\" id=\"solutie\" cols=\"50\" rows=\"3\"></textarea>";

          document.getElementById(divName).appendChild(newdiv);
          counter++;

     }
}
