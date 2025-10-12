package xyz.gamecrash.AnvilAccess.util.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class holding validation results
 */
public class ValidationResult {
    @Getter private final List<String> errors = new ArrayList<>();
    @Getter private final List<String> warnings = new ArrayList<>();
    @Getter private final List<String> infos = new ArrayList<>();

    public void addError(String error) { errors.add(error); }

    public void addWarning(String warning) { warnings.add(warning); }

    public void addInfo(String info) { infos.add(info); }

    public boolean isValid() { return errors.isEmpty();}

    public boolean hasErrors() { return !errors.isEmpty(); }

    public boolean hasWarnings() { return !warnings.isEmpty(); }

    public void merge(ValidationResult other, String prefix) {
        other.errors.forEach(err -> this.addError(prefix + ":" + err));
        other.warnings.forEach(warn -> this.addWarning(prefix + ":" + warn));
        other.infos.forEach(info -> this.addInfo(prefix + ":" + info));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Validation Result:\n");

        if (!errors.isEmpty()) {
            sb.append("ERRORS:\n");
            errors.forEach(error -> sb.append("  - ").append(error).append("\n"));
        }

        if (!warnings.isEmpty()) {
            sb.append("WARNINGS:\n");
            warnings.forEach(warning -> sb.append("  - ").append(warning).append("\n"));
        }

        if (!infos.isEmpty()) {
            sb.append("INFO:\n");
            infos.forEach(info -> sb.append("  - ").append(info).append("\n"));
        }

        if (errors.isEmpty() && warnings.isEmpty() && infos.isEmpty()) sb.append("No issues found.\n");

        return sb.toString();
    }
}
