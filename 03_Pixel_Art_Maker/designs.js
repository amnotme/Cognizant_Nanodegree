// Grid object to hold basic attributes.
grid = {
  defaultColor: "#ffffff",
  color: null,
  height: 1,
  width: 1,
  padding: ".2em",
  drawnGrid: [],
};

// height getter
const getInputHeight = function () {
  return document.getElementById("inputHeight").valueAsNumber;
};

// width getter
const getInputWidth = function () {
  return document.getElementById("inputWidth").valueAsNumber;
};

// color getter
const getColor = function () {
  return document.getElementById("colorPicker").value;
};

// resets original Grid to a regular table.
const resetGrid = function () {
  document.getElementById("pixelCanvas").innerHTML = "";
  grid.drawnGrid = [];
};

// sets background color of cell
const setColor = function (cell) {
  cell.style.backgroundColor = getColor();
};

// sets initial background color and padding size for the grid.
const setColorAndStyle = function (cell, color, size) {
  cell.style.padding = size;
  cell.style.backgroundColor = color;
};

// adds listener to the form.
const addListenerToForm = function () {
  document
    .getElementById("sizePicker")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      makeGrid();
    });
};

// fill a square with the color selected.
const fill = function (event) {
  setColor(event.target);
};

// add an event listener on grid creation.
const addCellEventListener = function (cell) {
  cell.addEventListener("click", fill);
};

// Creates the initial grid and redraws it when the submit form is submitted;
function makeGrid() {
  resetGrid();

  grid.drawnGrid = document.getElementById("pixelCanvas");
  grid.height = getInputHeight();
  grid.width = getInputWidth();
  grid.color = getColor();

  for (let i = 0; i < grid.height; i++) {
    const row = document.createElement("tr");
    for (let j = 0; j < grid.width; j++) {
      const cell = document.createElement("td");
      addCellEventListener(cell);
      setColorAndStyle(cell, grid.defaultColor, grid.padding);
      row.appendChild(cell);
    }
    grid.drawnGrid.appendChild(row);
  }
}

// Our starting point.  We want to set the listener to the submit button.
addListenerToForm();
