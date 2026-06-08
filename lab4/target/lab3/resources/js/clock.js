const canvas = document.getElementById('clock');
const c = canvas.getContext('2d');

const scale = 80;
canvas.width = scale * 2.5;
canvas.height = scale * 2.5;

function drawClock() {
    const now = new Date();
    const sec = now.getSeconds();
    const min = now.getMinutes();
    const hr = now.getHours() % 12;

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;

    c.clearRect(0, 0, canvas.width, canvas.height);

    c.beginPath();
    c.arc(centerX, centerY, scale, 0, Math.PI * 2);
    c.lineWidth = 6;
    c.strokeStyle = "#00ffe5";
    c.stroke();

    let hourAngle = ((Math.PI * 2) / 12) * hr + (min * Math.PI) / 360;
    c.beginPath();
    c.moveTo(centerX, centerY);
    c.lineTo(
        centerX + Math.cos(hourAngle - Math.PI / 2) * (scale * 0.5),
        centerY + Math.sin(hourAngle - Math.PI / 2) * (scale * 0.5)
    );
    c.lineWidth = 6;
    c.stroke();

    let minAngle = ((Math.PI * 2) / 60) * min;
    c.beginPath();
    c.moveTo(centerX, centerY);
    c.lineTo(
        centerX + Math.cos(minAngle - Math.PI / 2) * (scale * 0.75),
        centerY + Math.sin(minAngle - Math.PI / 2) * (scale * 0.75)
    );
    c.lineWidth = 4;
    c.stroke();

    let secAngle = ((Math.PI * 2) / 60) * sec;
    c.beginPath();
    c.moveTo(centerX, centerY);
    c.lineTo(
        centerX + Math.cos(secAngle - Math.PI / 2) * (scale * 0.85),
        centerY + Math.sin(secAngle - Math.PI / 2) * (scale * 0.85)
    );
    c.strokeStyle = "red";
    c.lineWidth = 2;
    c.stroke();

    c.beginPath();
    c.arc(centerX, centerY, 5, 0, Math.PI * 2);
    c.fillStyle = "red";
    c.fill();
}

function updateClock() {
    const now = new Date();

    const h = String(now.getHours()).padStart(2, "0");
    const m = String(now.getMinutes()).padStart(2, "0");
    const s = String(now.getSeconds()).padStart(2, "0");

    document.getElementById('time').textContent = `${h}:${m}:${s}`;

    document.getElementById('date').textContent = now.toLocaleDateString();

    drawClock();
}

setInterval(updateClock, 13000);
updateClock();
