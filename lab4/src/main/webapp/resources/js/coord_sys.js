const canvas = document.getElementById('coordCanvas');
const cs = canvas.getContext('2d');
const SCALE = 10;

function drawArrow(x, y, angle) {
    cs.save();
    cs.translate(x, y);
    cs.rotate(angle);
    cs.beginPath();
    cs.moveTo(0, 0);
    cs.lineTo(-10, -5);
    cs.moveTo(0, 0);
    cs.lineTo(-10, 5);
    cs.strokeStyle = '#ffffff';
    cs.lineWidth = 2;
    cs.stroke();
    cs.restore();
}

function drawCS() {
    cs.clearRect(0, 0, canvas.width, canvas.height);
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;

    drawArea(centerX, centerY);

    cs.strokeStyle = '#ffffff';
    cs.beginPath();
    cs.moveTo(20, centerY);
    cs.lineTo(canvas.width - 20, centerY);
    cs.moveTo(centerX, canvas.height - 20);
    cs.lineTo(centerX, 20);
    cs.stroke();

    drawArrow(canvas.width - 15, centerY, 0);
    drawArrow(centerX, 15, -Math.PI / 2);

    cs.fillStyle = 'rgba(255,255,255,0.6)';
    const scale = SCALE;
    for (let x = -16; x <= 16; x += 2) {
        if (x === 0) continue;
        const px = centerX + x * scale;
        cs.beginPath();
        cs.arc(px, centerY, 3, 0, Math.PI * 2);
        cs.fill();
        cs.fillStyle = 'rgba(255,255,255,0.8)';
        cs.font = '12px Arial';
        cs.textAlign = 'center';
        cs.fillText((x / 4).toString(), px, centerY + 20);
        cs.fillStyle = 'rgba(255,255,255,0.6)';
    }

    for (let y = -16; y <= 16; y += 2) {
        if (y === 0) continue;
        const py = centerY - y * scale;
        cs.beginPath();
        cs.arc(centerX, py, 3, 0, Math.PI * 2);
        cs.fill();
        cs.fillStyle = 'rgba(255,255,255,0.8)';
        cs.font = '12px Arial';
        cs.textAlign = 'left';
        cs.fillText((y / 4).toString(), centerX + 10, py + 4);
        cs.fillStyle = 'rgba(255,255,255,0.6)';
    }

    cs.fillStyle = '#ffffff';
    cs.font = '14px Arial';
    cs.fillText('X', canvas.width - 25, centerY - 10);
    cs.fillText('Y', centerX + 10, 25);
}

function drawArea(cx, cy) {
    const rValue = (typeof getBeanR === 'function') ? getBeanR() : 0;
    const pxR = (rValue > 0) ? (40 * rValue) : 40;

    const centerX = cx;
    const centerY = cy;

    cs.save();

    cs.fillStyle = "rgba(0,120,255,0.35)";
    cs.strokeStyle = "#00F7FF";
    cs.lineWidth = 2;

    cs.beginPath();
    cs.moveTo(centerX, centerY);
    cs.lineTo(centerX + pxR, centerY);
    cs.lineTo(centerX, centerY - pxR);
    cs.closePath();
    cs.fill();
    cs.stroke();

    cs.beginPath();
    cs.rect(centerX - pxR/2, centerY, pxR/2, pxR);
    cs.fill();
    cs.stroke();

    cs.beginPath();
    cs.moveTo(centerX, centerY);
    cs.arc(centerX, centerY, pxR, 0, Math.PI / 2, false);
    cs.closePath();
    cs.fill();
    cs.stroke();

    cs.restore();
}

function drawPoint(x, y, r, color = 'red') {
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const x_value = centerX + (4 * x * SCALE);
    const y_value = centerY - (4 * y * SCALE);
    cs.fillStyle = color;
    cs.beginPath();
    cs.arc(x_value, y_value, 5, 0, 2 * Math.PI);
    cs.fill();
}

function drawAllPointsFromBean() {
    if (typeof points !== 'undefined' && Array.isArray(points)) {
        drawCS();
        points.forEach(p => {
            const x = parseFloat(p.x);
            const y = parseFloat(p.y);
            const r = parseFloat(p.r);
            drawPoint(x, y, r, p.success ? 'lime' : 'red');
        });
    }
}

function clearCanvasOnly() {
    cs.clearRect(0, 0, canvas.width, canvas.height);
    drawCS();
}

function getBeanR() {
    const val = document.getElementById('beanR').value;
    const parsed = parseFloat(val);
    return isNaN(parsed) ? 0 : parsed;
}


document.addEventListener('DOMContentLoaded',()=>{
    drawCS();
    drawAllPointsFromBean();
});
