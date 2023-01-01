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
const widgetTargetFormSelectors = ".target-form";
const removeButtonSelectors = ".remove-btn-confirm";
const sortInputSelectors = ".sort-input";
const instanceNameSelectors = ".widget-instance-name";

const quillToolbarOptions = [
    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    ['blockquote', 'code-block'],

    [{'header': 1}, {'header': 2}],               // custom button values
    [{'list': 'ordered'}, {'list': 'bullet'}],
    [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
    [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
    [{'direction': 'rtl'}],                         // label direction

    [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
    [{'header': [1, 2, 3, 4, 5, 6, false]}],

    [{'color': []}, {'background': []}],          // dropdown with defaults from theme
    [{'font': []}],
    [{'align': []}],

    ['link', 'image'],

    ['clean']                                         // remove formatting button
];

const setupDragElements = function () {
    const elements = document.querySelectorAll(dragSelectors);

    for (let i = 0; i < elements.length; i++) {
        let el = elements[i];
        el.addEventListener("dragstart", function (ev) {
            ev.dataTransfer.effectAllowed = "copyMove";
            ev.dataTransfer.setData("text/plain", ev.target.id);
        });

        el.addEventListener("drop", (ev) => {
            return false;
        });
    }
}

const updateWidgetInstancePosition = function (parentEl, position) {
    const positionEl = parentEl.querySelector('input[name=position]');
    if (positionEl) {
        positionEl.value = position;
    }
}


const drop = function (ev) {
    ev.preventDefault();
    let draggingId = ev.dataTransfer.getData("text/plain");
    let draggedEl = document.getElementById(draggingId);
    if (draggedEl === null) {
        return {action: "nothing", el: null};
    }

    const newPosition = ev.target.id.toUpperCase();

    // For copied node, just move it
    if (draggedEl.classList.contains("copied")) {
        ev.target.appendChild(draggedEl);
        updateWidgetInstancePosition(draggedEl, newPosition);
        return {action: "copy", el: draggedEl};
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
    return {action: "copy", el: copiedNode};
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

const createHiddenInput = function (parentEl, name, value) {
    const inputEl = document.createElement("input");
    inputEl.id = value;
    inputEl.name = name;
    inputEl.type = "hidden";
    inputEl.value = value;
    parentEl.appendChild(inputEl);
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
        indentUnit: 4,
        matchBrackets: true,
        // `autoRefresh` is to solve line number misaligned
        // https://github.com/codemirror/codemirror5/issues/3098#issuecomment-147022207
        autoRefresh: true,
        theme: 'monokai',
        mode: "htmlmixed",
    });

    editor.setOption("extraKeys", {
        // Tab to 4 spaces
        "Tab": function (cm) {
            var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
            cm.replaceSelection(spaces);
        },
        // Esc to toggle fullscreen
        "Esc": function (cm) {
            cm.setOption("fullScreen", !cm.getOption("fullScreen"));
        }
    });

    editor.on("changes", function (cm) {
        el.value = cm.getValue();
        let form = el.closest('form');
        triggerFormChangeEvent(form);
    });
}

const triggerFormChangeEvent = function (formEl) {
    formEl.dispatchEvent(new Event('change'));
}

const removeDisabled = function (el) {
    const buttons = el.querySelectorAll('button');
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].disabled = false;
    }

    el.querySelector(sortInputSelectors).disabled = false;
    el.querySelector(instanceNameSelectors).disabled = false;
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
        if (!Reflect.has(object, key)) {
            object[key] = value;
            return;
        }
        if (!Array.isArray(object[key])) {
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
            const targetForm = _form.closest(dragSelectors).querySelector(widgetTargetFormSelectors);
            const data = new FormData(_form);
            targetForm['configuration'].value = convertFormDataToJson(data);
        });
    }
}

const setupWidgetTargetForm = function () {
    const forms = document.querySelectorAll(widgetTargetFormSelectors);
    for (let i = 0; i < forms.length; i++) {
        let form = forms[i];
        form.addEventListener('submit', function (ev) {
            ev.preventDefault();
            saveForm(this);
        });
    }
}

const setupRemoveButton = function (callback) {
    const elements = document.querySelectorAll(removeButtonSelectors);
    for (let i = 0; i < elements.length; i++) {
        let el = elements[i];
        let dragItem = el.closest(dragSelectors);
        el.addEventListener("click", function (ev) {
            let form = dragItem.querySelector(widgetTargetFormSelectors);
            let deleteUrl = form.getAttribute("data-delete-url");
            let instanceId = form["id"].value;

            callback(deleteUrl + "?id=" + instanceId);
            dragItem.remove();
        });
    }
}

const showSuccess = function (message) {
    Swal.fire({
        position: 'top-end',
        icon: 'success',
        text: message,
        showConfirmButton: false,
        timer: 1500
    });
}

const isString = function (input) {
    return typeof input === 'string' || input instanceof String;
}

const isArray = function (input) {
    return input.constructor === Array;
}

/**
 * Use sweet alert show error
 *
 * @param errors can be `String` or string `Array`
 */
const showErrors = function (errors) {
    if (isString(errors)) {
        Swal.fire({icon: 'error', html: errors});
        return;
    }

    if (isArray(errors)) {
        let errorHtmls = '';
        for (let s of errors) {
            errorHtmls += '<div>' + s + '</div>';
        }
        Swal.fire({icon: 'error', html: errorHtmls});
    }
}

const saveForm = function (formEl) {
    if (formEl == null) {
        console.error("Form element didn't specify!")
        return;
    }

    const url = formEl.action;
    const formData = convertFormDataToJson(new FormData(formEl));

    postData(url, formData)
        .then(json => formEl["id"].value = json.id);
}

const postData = function (url, data) {
    return fetch(url, {
        method: 'POST',
        headers: {"Content-Type": "application/json"},
        body: data
    }).then((response) => {
        if (!response.ok) {
            response.json().then((json) => showErrors(json));
            throw new Error("Response status from API is " + response.status);
        } else {
            return response.json();
        }
    }).then((json) => {
        showSuccess("Saved");
        return json;
    });
};

const deleteData = async function (url) {
    return fetch(url, {method: 'DELETE'})
        .then((response) => {
            if (!response.ok) {
                response.json().then((json) => showErrors(json));
                throw new Error("Response status from API is " + response.status);
            } else {
                return response.json();
            }
        }).then((json) => {
            showSuccess("Deleted");
            return json;
        });
};

const setupSortInputs = function () {
    const sortInputs = document.querySelectorAll(sortInputSelectors);
    for (let i = 0; i < sortInputs.length; i++) {
        let inputEl = sortInputs[i];
        inputEl.addEventListener("change", function () {
            let dragItem = inputEl.closest(dragSelectors);
            let id = dragItem.querySelector('input[name=id]').value;
            let sortUrl = dragItem.querySelector(widgetTargetFormSelectors).getAttribute("data-sort-url");

            // save to backend
            const params = new URLSearchParams({id: id, sort: inputEl.value});
            postData(sortUrl + "?" + params.toString())
                .then(() => {
                    // sort frontend elements
                    let dropArea = inputEl.closest(dropSelectors);
                    sortChildrenElements(dropArea, sortInputSelectors);
                })
        });
    }
}

const sortChildrenElements = function (el, inputSelector) {
    let nodes = el.childNodes;
    let elements = [];
    for (let i in nodes) {
        if (nodes[i].nodeType === Node.ELEMENT_NODE) {
            elements.push(nodes[i]);
        }
    }

    elements.sort(function (a, b) {
        let aSort = parseInt(a.querySelector(inputSelector).value);
        let bSort = parseInt(b.querySelector(inputSelector).value);

        return aSort === bSort ? 0 : (aSort > bSort ? 1 : -1);
    });

    for (let j = 0; j < elements.length; ++j) {
        el.appendChild(elements[j]);
    }
}

const setupWidgetInstanceNames = function () {
    const elements = document.querySelectorAll(instanceNameSelectors);
    for (let i = 0; i < elements.length; i++) {
        let inputEl = elements[i];
        inputEl.addEventListener("dblclick", function () {
            this.readOnly = false;
            this.classList.remove('form-control-plaintext');
            this.classList.add('form-control')
        });

        inputEl.addEventListener("blur", function () {
            this.readOnly = true;
            this.classList.add('form-control-plaintext');
            this.classList.remove('form-control')
        })

        inputEl.addEventListener("change", function () {
            let dragItem = inputEl.closest(dragSelectors);
            let id = dragItem.querySelector('input[name=id]').value;
            let sortUrl = dragItem.querySelector(widgetTargetFormSelectors).getAttribute("data-name-url");

            // save to backend
            const params = new URLSearchParams({id: id, name: inputEl.value});
            postData(sortUrl + "?" + params.toString());
        });

    }
}


const setupImageDropzone = function () {
    const dropzoneEls = document.querySelectorAll(".dropzone");
    for (let i = 0; i < dropzoneEls.length; i++) {
        let dropzoneEl = dropzoneEls[i];
        if (dropzoneEl.dropzone) {
            continue;
        }

        let maxFilesize = parseInt(dropzoneEl.getAttribute("max-filesize"));
        let maxFiles = parseInt(dropzoneEl.getAttribute("max-files"));
        new Dropzone(dropzoneEl, {
            url: "/admin/api/upload",
            dictCancelUpload: "Cancel",
            dictRemoveFile: "Remove",
            addRemoveLinks: true,
            maxFilesize: maxFilesize >= 0 ? maxFilesize : null,
            acceptedFiles: dropzoneEl.getAttribute("accepted-files"),
            maxFiles: parseInt(dropzoneEl.getAttribute("max-files")),
            success: function (file, response) {
                let name = this.element.getAttribute("data-name");
                let formEl = this.element.closest('form');
                let parentEl = this.element.closest('.image-dropzone');
                createHiddenInput(parentEl, name, response.uri);
                triggerFormChangeEvent(formEl);

                file.id = response.uri;
                file.previewElement.classList.add("dz-success");
            },
            removedfile: function (file) {
                let formEl = this.element.closest('form');
                removeHiddenInput(file.id);
                triggerFormChangeEvent(formEl);
                file.previewElement.remove();
            },
            maxfilesexceeded: function (file) {
                this.removeFile(file);
                showErrors("File Limit exceeded!");
            },
            init: function () {
                loadExistedPictures(this, maxFiles);
            }
        });
    }
}

const loadExistedPictures = function (dropzoneObject, maxFiles) {
    const imageDropzone = dropzoneObject.element.closest(".image-dropzone");
    const hiddenInputs = imageDropzone.querySelectorAll('input[type=hidden]');

    for (let j = 0; j < hiddenInputs.length && j < maxFiles; j++) {
        let inputEl = hiddenInputs[j];
        const uri = inputEl.value;
        let imageUrl = uploadBaseUrl + uri;
        let mockFile = {
            id: uri,
            name: uri,
            status: Dropzone.ADDED,
        };

        let callback = null; // Optional callback when it's done
        let crossOrigin = "Anonymous"; // Added to the `img` tag for crossOrigin handling
        let resizeThumbnail = true; // Tells Dropzone whether it should resize the image first

        dropzoneObject.displayExistingFile(mockFile, imageUrl, callback, crossOrigin, resizeThumbnail);
        dropzoneObject.files.push(mockFile);
    }
}

const setupRichTextEditor = function (rootEl) {
    const elements = rootEl.querySelectorAll(".editor");
    for (let i = 0; i < elements.length; i++) {
        let editorEl = elements[i];
        editorEl.innerHTML = '';
        editorEl.parentNode.querySelectorAll('.ql-toolbar').forEach(x => x.remove());

        let inputName = editorEl.getAttribute('data-input-name');
        let inputEl = editorEl.parentNode.querySelector(`input[name=${inputName}]`);

        let quill = new Quill(editorEl, {
            modules: {toolbar: quillToolbarOptions},
            theme: 'snow'
        });
        let delta = quill.clipboard.convert(inputEl.value)
        quill.setContents(delta, 'silent')

        let form = inputEl.closest('form');
        quill.on('text-change', function () {
            inputEl.value = quill.root.innerHTML;
            triggerFormChangeEvent(form);
        });
    }
}