// add trim() to String
if (typeof (String.prototype.trim) === "undefined") {
    String.prototype.trim = function () {
        return String(this).replace(/^\s+|\s+$/g, '');
    };
}

// add trimSlash() to String
if (typeof (String.prototype.trimSlash) === "undefined") {
    String.prototype.trimSlash = function () {
        return String(this).replace(/^-+|-+$/g, '');
    };
}

const addListener = function (cssClassName, listener, callFunction) {
    document.addEventListener(listener, function (e) {
        if (e.target.classList.contains(cssClassName)) {
            callFunction(e);
        }
    }, false);
}

const isBefore = function (el1, el2) {
    if (el1 === null || el1 === undefined || el2 === null || el2 === undefined)
        return false;

    if (el2.parentNode === el1.parentNode)
        for (var cur = el1.previousSibling; cur && cur.nodeType !== 9; cur = cur.previousSibling)
            if (cur === el2)
                return true;
    return false;
}

const createHiddenInput = function (name, value) {
    const inputEl = document.createElement("input");
    inputEl.id = value;
    inputEl.name = name;
    inputEl.type = "hidden";
    inputEl.value = value;
    mainForm.appendChild(inputEl);
}

const removeHiddenInput = function (fileId) {
    const inputEl = document.getElementById(fileId);
    if (inputEl !== undefined && inputEl !== null) {
        inputEl.remove();
    }
}

const creatHtmlEditor = function (el) {
    return CodeMirror.fromTextArea(el, {
        lineNumbers: true,
        lineWrapping: true,
        mode: "htmlmixed",
    });
}

const initHtmlEditor = function (textareaClassName) {
    const textareas = document.querySelectorAll("." + textareaClassName);
    for (let i = 0; i < textareas.length; i++) {
        creatHtmlEditor(textareas[i]);
    }

    document.addEventListener("paste", function (e) {
        if (e.target.classList.contains(textareaClassName)) {
            creatHtmlEditor(e.target);
        }
    }, false);
}