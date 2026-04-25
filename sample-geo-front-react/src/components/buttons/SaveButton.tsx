import SaveIcon from "@mui/icons-material/Save";
import { IconButton, Tooltip } from "@mui/material";

type Props = {
  disabled?: boolean;
  tooltip?: string;
  onClick: () => void;
};

export default function SaveButton({
  disabled = false,
  tooltip = "Save",
  onClick,
}: Props) {
  return (
    <Tooltip title={tooltip}>
      <IconButton onClick={onClick} color="primary" disabled={disabled}>
        <SaveIcon />
      </IconButton>
    </Tooltip>
  );
}
