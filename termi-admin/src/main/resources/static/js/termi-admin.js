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

const dropSelectors = ".drop-area";
const dragSelectors = ".drag-item";
const htmlEditorSelectors = ".html-editor";

const setupDragElements = function () {
    const elements = document.querySelectorAll(dragSelectors);

    for (let i = 0; i < elements.length; i++) {
        let el = elements[i];
        el.addEventListener("dragstart", function (ev) {
            ev.dataTransfer.effectAllowed = "copyMove";
            ev.dataTransfer.setData("text/plain", ev.target.id);
        });

        el.addEventListener("drop",  (ev) => {return false;});
    }
}

const drop = function (ev) {
    ev.preventDefault();
    let draggingId = ev.dataTransfer.getData("text/plain");
    let draggedEl = document.getElementById(draggingId);
    if (draggedEl === null) {
        return;
    }

    // For copied node, just move it
    if (draggedEl.classList.contains("copied")) {
        ev.target.appendChild(draggedEl);
        return;
    }

    // Otherwise, copy a new node, and give it a new id
    const now = new Date().getTime();
    const copiedNode = draggedEl.cloneNode(true);
    copiedNode.id = "widget-" + now;
    copiedNode.classList.add("copied");

    // Set copied card body a new id
    const newCardBodyId = 'collapse-' + copiedNode.id;
    const cardBody = copiedNode.getElementsByClassName("card-body")[0];
    const collapseBtn = copiedNode.getElementsByClassName("collapse-btn")[0];
    cardBody.setAttribute('id', newCardBodyId);
    collapseBtn.setAttribute('data-bs-target', '#' + newCardBodyId);
    collapseBtn.setAttribute('aria-controls', newCardBodyId);

    // Set preview body a new id
    const newPreviewId = 'preview-' + copiedNode.id;
    const previewBody = copiedNode.getElementsByClassName("preview-body")[0];
    const previewBtn = copiedNode.getElementsByClassName("preview-btn")[0];
    previewBody.setAttribute('id', newPreviewId);
    previewBody.classList.remove('active', 'show');
    previewBtn.setAttribute('data-bs-target', '#' + newPreviewId);
    previewBtn.classList.remove('active');

    // Set config body a new id
    const newConfigId = 'config-' + copiedNode.id;
    const configBody = copiedNode.getElementsByClassName("config-body")[0];
    const configBtn = copiedNode.getElementsByClassName("config-btn")[0];
    configBody.setAttribute('id', newConfigId);
    configBody.classList.add('active', 'show');
    configBtn.setAttribute('data-bs-target', '#' + newConfigId);
    configBtn.classList.add('active');

    // append copied card to drop area
    ev.target.appendChild(copiedNode);
    return copiedNode;
}

const setupDropArea = function () {
    const elements = document.querySelectorAll(dropSelectors);
    for (let i = 0; i < elements.length; i++) {
        let el = elements[i];
        el.addEventListener("drop", (ev) => {
            let copiedNode = drop(ev);
            setupDragElements();
            setupHtmlEditor(copiedNode);
        });
        el.addEventListener("dragenter", (ev) => ev.target.classList.add("border-dashed"));
        el.addEventListener("dragleave", (ev) => ev.target.classList.remove("border-dashed"));
        el.addEventListener("dragover", (ev) => ev.preventDefault());
    }
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
    const editor = CodeMirror.fromTextArea(el, {
        lineNumbers: true,
        lineWrapping: true,
        mode: "htmlmixed",
    });
}

const setupHtmlEditor = function (rootEl) {
    if (!rootEl) {
        return;
    }

    const textareas = rootEl.querySelectorAll(htmlEditorSelectors);
    for (let i = 0; i < textareas.length; i++) {
        let textareaEl = textareas[i];
        let next = textareaEl.nextElementSibling;
        if (next != null && next.classList.contains("CodeMirror")) {
            textareaEl.nextElementSibling.remove()
        }
        creatHtmlEditor(textareaEl);
    }
}