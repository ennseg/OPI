$(document).ready(function() {
    const form = $('#dataForm');
    const area = document.getElementById("coordCanvas");
    const responseBanner = $('#response');

    function showResponseBanner(message) {
        responseBanner.html(message);
        responseBanner.css('opacity', 1);
        setTimeout(() => responseBanner.css('opacity', 0), 3000);
    }

    function validation_Y(Y) {
        const pattern = /^-?\d+(\.\d+)?$/;
        if (!pattern.test(Y)) return false;
        const y_val = parseFloat(Y);
        return y_val > -5 && y_val < 3;
    }

    function validation_X(X) {
        const pattern = /^-?\d+(\.\d+)?$/;
        if (!pattern.test(X)) return false;
        const x_val = parseFloat(X);
        return x_val > -5 && x_val < 5;
    }

    function validation_R(R) {
        return ["1","2","3","4","5"].includes(String(R));
    }

    area.addEventListener("click", function(event) {
        const selectedR = $('input[name="R"]:checked').val() || document.getElementById('beanR').value;
        if (!selectedR || !validation_R(selectedR)) {
            showResponseBanner("Select R first");
            return;
        }

        const rect = area.getBoundingClientRect();
        const mouseX = event.clientX - rect.left;
        const mouseY = event.clientY - rect.top;

        const centerX = area.width / 2;
        const centerY = area.height / 2;
        const Rpx = 40;

        const x = ((mouseX - centerX) / (Rpx / selectedR) /selectedR).toFixed(2);
        const y = ((centerY - mouseY) / (Rpx / selectedR) /selectedR).toFixed(2);

        $('#X').val(x);
        $('#Y').val(y);
        $(`input[name="R"][value="${selectedR}"]`).prop('checked', true);
        $('#beanR').val(selectedR);

        if (typeof PF !== 'undefined' && PF('hiddenSubmit')) {
            PF('hiddenSubmit').jq.trigger('click');
        } else {
            form.submit();
        }
    });
});
