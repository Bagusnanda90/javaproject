/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
        config.height = '800px';
		config.extraPlugins = 'filebrowser';
		config.extraPlugins = 'texttransform';
		config.filebrowserBrowseUrl = '../styles/Filemanager/index.html';
		config.filebrowserImageBrowseUrl = '../styles/Filemanager/index.html?type=Images&currentFolder=/Image/';
		config.filebrowserFlashBrowseUrl = '../styles/Filemanager/index.html?type=Flash&currentFolder=/Flash/';
		config.filebrowserUploadUrl = '../styles/Filemanager/connectors/jsp/filemanager.jsp?mode=add&type=Files&currentFolder=/File/';
		config.filebrowserImageUploadUrl = '../styles/Filemanager/connectors/jsp/filemanager.jsp?mode=add&type=Images&currentFolder=/Image/';
		config.filebrowserFlashUploadUrl = '../styles/Filemanager/connectors/jsp/filemanager.jsp?mode=add&type=Flash&currentFolder=/Flash/';
		
		
		
};
