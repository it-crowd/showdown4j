//
//  Image Extension
//  (( :id ))   ->  <img src="/api/screen/data/:id"/>
//

(function ()
{

    var image = function (converter)
    {
        return [

            { type: 'lang', regex: '\\(\\(\\s*\\d+\\s*\\)\\)', replace: function (match)
            {
                return '<img src="/api/screen/data/' + match.replace(/\(|\)/g, '').replace(/^\s+|\s+$/g, '') + '"/>';
            }}

        ];
    };

    // Client-side export
    if (Showdown && Showdown.extensions) {
        Showdown.extensions.image = image;
    }
    // Server-side export
    if (typeof module !== 'undefined') {
        module.exports = image;
    }

}());