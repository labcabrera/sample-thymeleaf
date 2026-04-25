import { IconButton, Tooltip } from "@mui/material";
import CancelIcon from "@mui/icons-material/Cancel";

type Props = {
  disabled?: boolean;
  tooltip?: string;
  onClick: () => void;
};

export default function CancelButton({
  disabled = false,
  tooltip = "Cancel",
  onClick,
}: Props) {
  return (
    <Tooltip title={tooltip}>
      <IconButton onClick={onClick} color="primary" disabled={disabled}>
        <CancelIcon />
      </IconButton>
    </Tooltip>
  );
}
