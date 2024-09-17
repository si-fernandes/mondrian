/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package mondrian.olap;

import java.util.Locale;

/**
 * An <code>OlapElement</code> is a catalog object (dimension, hierarchy,
 * level, member).
 *
 * @author jhyde, 21 January, 1999
 */
public interface OlapElement {
    String getUniqueName();
    String getName();

    String getDescription();

    /**
     * Looks up a child element, returning null if it does not exist.
     */
    OlapElement lookupChild(
        SchemaReader schemaReader,
        Id.Segment s,
        MatchType matchType);

    /**
     * Returns the name of this element qualified by its class, for example
     * "hierarchy 'Customers'".
     */
    String getQualifiedName();

    String getCaption();

    /**
     * Returns the value of a property (caption or description) of
     * this element in the given locale.
     *
     * @param locale Locale
     * @return Localized caption or description
     */
    String getLocalized(LocalizedProperty prop, Locale locale);

    Hierarchy getHierarchy();

    /**
     * Returns the dimension of a this expression, or null if no dimension is
     * defined. Applicable only to set expressions.
     *
     * <p>Example 1:
     * <blockquote><pre>
     * [Sales].children
     * </pre></blockquote>
     * has dimension <code>[Sales]</code>.</p>
     *
     * <p>Example 2:
     * <blockquote><pre>
     * order(except([Promotion Media].[Media Type].members,
     *              {[Promotion Media].[Media Type].[No Media]}),
     *       [Measures].[Unit Sales], DESC)
     * </pre></blockquote>
     * has dimension [Promotion Media].</p>
     *
     * <p>Example 3:
     * <blockquote><pre>
     * CrossJoin([Product].[Product Department].members,
     *           [Gender].members)
     * </pre></blockquote>
     * has no dimension (well, actually it is [Product] x [Gender], but we
     * can't represent that, so we return null);</p>
     */
    Dimension getDimension();

    /**
     * Returns whether this element is visible to end-users.
     *
     * <p>Visibility is a hint for client applications. An element's visibility
     * does not affect how it is treated when MDX queries are evaluated.
     *
     * @return Whether this element is visible
     */
    boolean isVisible();

    enum LocalizedProperty {
        CAPTION,
        DESCRIPTION
    }
}

// End OlapElement.java
