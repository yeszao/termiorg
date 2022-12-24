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
const widgetSourceFormSelectors = ".source-form";
const widgetTargetFormClass = "target-form";
const widgetTargetFormSelectors = "." + widgetTargetFormClass;

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

const updateWidgetInstancePosition = function (parentEl, position) {
    const positionEl = parentEl.querySelector("input[name=position]");
    if (positionEl) {
        positionEl.value = position;
    }
}


const drop = function (ev) {
    ev.preventDefault();
    let draggingId = ev.dataTransfer.getData("text/plain");
    let draggedEl = document.getElementById(draggingId);
    if (draggedEl === null) {
        return;
    }

    const newPosition = ev.target.id.toUpperCase();

    // For copied node, just move it
    if (draggedEl.classList.contains("copied")) {
        ev.target.appendChild(draggedEl);
        updateWidgetInstancePosition(draggedEl, newPosition);
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
    updateWidgetInstancePosition(copiedNode, newPosition);
    return copiedNode;
}

const setupDropArea = function (dropCallback) {
    const elements = document.querySelectorAll(dropSelectors);
    for (let i = 0; i < elements.length; i++) {
        let el = elements[i];
        el.addEventListener("drop", dropCallback);
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

    editor.on("change", function (cm) {
        el.value = cm.getValue();
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


const convertFormDataToJson = function (formData) {
    var object = {};
    formData.forEach((value, key) => {
        // Reflect.has in favor of: object.hasOwnProperty(key)
        if(!Reflect.has(object, key)){
            object[key] = value;
            return;
        }
        if(!Array.isArray(object[key])){
            object[key] = [object[key]];
        }
        object[key].push(value);
    });

    return JSON.stringify(object);
}


const setupWidgetSourceForm = function () {
    const forms = document.querySelectorAll(widgetSourceFormSelectors);
    for (let i = 0; i < forms.length; i++) {
        let form = forms[i];
        form.addEventListener('change', function (ev) {
            const _form = this;
            const targetForm = _form.nextElementSibling;

            if (targetForm.classList.contains(widgetTargetFormClass)) {
                const data = new FormData(_form);
                targetForm['configuration'].value = convertFormDataToJson(data);
            }
        });
    }
}

const setupWidgetTargetForm = function () {
    const forms = document.querySelectorAll(widgetTargetFormSelectors);
    for (let i = 0; i < forms.length; i++) {
        let form = forms[i];
        form.addEventListener('submit', function (ev) {
            ev.preventDefault();
            const url = this.action;
            let result = postData(url, this);

            console.log(result);
        });
    }
}

const postData = async function (url, data) {
    const response = await fetch(url, {
        method: 'POST',
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    return response.json();
};