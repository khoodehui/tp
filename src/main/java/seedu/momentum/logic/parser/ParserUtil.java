package seedu.momentum.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.core.Theme;
import seedu.momentum.commons.core.Time;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    /**
     * Parses {@code Optional<String> date} and {@code Optional<String> time}into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Deadline parseDeadline(Optional<String> date, Optional<String> time, Date createdDate)
            throws ParseException {
        if (date.isEmpty() || date.get().isBlank()) {
            return new Deadline();
        }

        String trimmedDate = date.get().trim();
        if (!Date.isValid(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }

        if (Deadline.isBeforeCreatedDate(trimmedDate, createdDate)) {
            throw new ParseException(Deadline.CREATED_DATE_MESSAGE_CONSTRAINT);
        }

        if (time.isEmpty() || time.get().isBlank()) {
            return new Deadline(trimmedDate, createdDate);
        }

        String trimmedTime = time.get().trim();
        if (!Time.isValid(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }

        return new Deadline(trimmedDate, trimmedTime, createdDate);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String theme} into a {@code Theme}.
     *
     * @throws ParseException if the given {@code theme} is invalid.
     */
    public static Theme parseTheme(String theme) throws ParseException {
        requireNonNull(theme);
        String trimmedTheme = theme.trim();
        try {
            Theme.ThemeType themeType = Theme.ThemeType.valueOf(trimmedTheme.toUpperCase());
            return new Theme(themeType);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Theme.MESSAGE_CONSTRAINTS);
        }
    }
}
