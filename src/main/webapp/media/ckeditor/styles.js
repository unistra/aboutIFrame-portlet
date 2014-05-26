
CKEDITOR.stylesSet.add( 'styles',
[
 /**
  * Portlet Styles for CKEditor
  * from JSR168 PLT.C - CSS Style Definitions
  * → https://wiki.jasig.org/display/UPC/JSR-168+PLT.C+CSS+Style+Definitions
  */
  // PLT.C.2 - Fonts
  {	name: 'Normal Font',	element: 'span',	attributes: { 'class': 'portlet-font' } },
  {	name: 'Lighter Font',	element: 'span',	attributes: { 'class': 'portlet-font-dim' } },
  // PLT.C.3 - Messages
  {	name: 'Highlight',	element: 'div',	attributes: { 'class': 'highlight' } },
  {	name: 'Status Message',	element: 'div',	attributes: { 'class': 'portlet-msg-status portlet-msg status', role: 'status' } },
  {	name: 'Info Message',	element: 'div',	attributes: { 'class': 'portlet-msg-info portlet-msg info', role: 'status'  } },
  {	name: 'Error Message',	element: 'div',	attributes: { 'class': 'portlet-msg-error portlet-msg error', role: 'alert'  } },
  {	name: 'Alert Message',	element: 'div',	attributes: { 'class': 'portlet-msg-alert portlet-msg alert', role: 'alert'  } },
  {	name: 'Success Message', element: 'div',	attributes: { 'class': 'portlet-msg-success portlet-msg success', role: 'status'  } },
  // PLT.C.4 - Sections
  {	name: 'Section', element: 'div',	attributes: { 'class': 'portlet-section', role: 'region'  } },
  {	name: 'Section header', element: 'div',	attributes: { 'class': 'portlet-section-header', role: 'section'  } },
  {	name: 'Section subheader', element: 'div',	attributes: { 'class': 'portlet-section-subheader', role: 'section'  } },
  {	name: 'Section body', element: 'div',	attributes: { 'class': 'portlet-section-body', role: 'section'  } },
  {	name: 'Section alternate', element: 'div',	attributes: { 'class': 'portlet-section-alternate', role: 'section'  } },
  {	name: 'Section selected', element: 'div',	attributes: { 'class': 'portlet-section-selected', role: 'section'  } },
  {	name: 'Section text', element: 'div',	attributes: { 'class': 'portlet-section-text', role: 'section'  } },
  {	name: 'Section note', element: 'div',	attributes: { 'class': 'portlet-section-note', role: 'note'  } },
  {	name: 'Section footer', element: 'div',	attributes: { 'class': 'portlet-section-footer', role: 'section'  } },

  /**
   * FSS Styles for CKEditor
   */
    // FSS Text
    // http://wiki.fluidproject.org/display/Infusion13/FSS+Text+-+Font+size
    {	name: '70% Font Size',	element: 'span',	attributes: { 'class': 'fl-font-size-70' } },
    {	name: '100% Font Size',	element: 'span',	attributes: { 'class': 'fl-font-size-100' } },
    {	name: '120% Font Size',	element: 'span',	attributes: { 'class': 'fl-font-size-120' } },
    {	name: '150% Font Size',	element: 'span',	attributes: { 'class': 'fl-font-size-150' } },
    // http://wiki.fluidproject.org/display/Infusion13/FSS+Text+-+Font+family
    {	name: 'Serif Font',	element: 'span',	attributes: { 'class': 'fl-font-serif' } },
    {	name: 'Times Font',	element: 'span',	attributes: { 'class': 'fl-font-times' } },
    {	name: 'Sans-serif Font',	element: 'span',	attributes: { 'class': 'fl-font-sans' } },
    {	name: 'Arial Font',	element: 'span',	attributes: { 'class': 'fl-font-arial' } },
    {	name: 'Verdana Font',	element: 'span',	attributes: { 'class': 'fl-font-verdana' } },
    {	name: 'Comic Sans Font',	element: 'span',	attributes: { 'class': 'fl-font-comic-sans' } },
    {	name: 'Monospaced Font',	element: 'span',	attributes: { 'class': 'fl-font-monospace' } },
    {	name: 'Courier Font',	element: 'span',	attributes: { 'class': 'fl-font-courier' } },
    // http://wiki.fluidproject.org/display/Infusion13/FSS+Text+-+Letter+spacing
    {	name: 'Default letter spacing',	element: 'span',	attributes: { 'class': 'fl-font-spacing-0' } },
    {	name: '.2 letter spacing',	element: 'span',	attributes: { 'class': 'fl-font-spacing-2' } },
    {	name: '.4 letter spacing',	element: 'span',	attributes: { 'class': 'fl-font-spacing-4' } },
    // http://wiki.fluidproject.org/display/Infusion13/FSS+Text+-+Colours+and+Highlight+colours
    {	name: 'Yellow Highlight',	element: 'p',	attributes: { 'class': 'fl-highlight-yellow' } },
    {	name: 'Green Highlight',	element: 'p',	attributes: { 'class': 'fl-highlight-green' } },
    {	name: 'Blue Highlight',	element: 'p',	attributes: { 'class': 'fl-highlight-blue' } },
    // http://wiki.fluidproject.org/display/Infusion13/FSS+Text+-+Styles
    {	name: 'Outlined',	element: 'p',	attributes: { 'class': 'fl-input-outline' } }
]);